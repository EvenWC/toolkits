/**
 * 
 */
package priv.wangcheng.zeus.security.demo.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import priv.wangcheng.exception.Assert;
import priv.wangcheng.zeus.security.demo.dto.User;
import priv.wangcheng.zeus.security.demo.dto.UserQueryCondition;

/**
 * @author zhailiang
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private ProviderSignInUtils providerSignInUtils;

	/*@Autowired
	private AppSignUpUtils appSignUpUtils;*/

	@PostMapping("/regist")
	public void regist(User user, HttpServletRequest request) {
		//不管是注册用户还是绑定用户，都会拿到一个用户唯一标识。
		String userId = user.getUsername();
		providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));
	}
   /* @PostMapping("app/regist")
    public void appRegist(User user, HttpServletRequest request) {
        //不管是注册用户还是绑定用户，都会拿到一个用户唯一标识。
        String userId = user.getUsername();
        appSignUpUtils.doPostSignUp(userId, new ServletWebRequest(request));
    }*/
	@GetMapping("/me")
	public Object getCurrentUser(@AuthenticationPrincipal Authentication user) {
		//return user;
		Assert.nonNull(user,"当前用户未登陆");
        Object principal = user.getPrincipal();
        System.out.println(principal);
        return user;
	}

	@PostMapping
	@ApiOperation(value = "创建用户")
	public User create(@Valid @RequestBody User user) {

		System.out.println(user.getId());
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getBirthday());

		user.setId("1");
		return user;
	}

	@PutMapping("/{id:\\d+}")
	public User update(@Valid @RequestBody User user, BindingResult errors) {

		System.out.println(user.getId());
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getBirthday());

		user.setId("1");
		return user;
	}

	@DeleteMapping("/{id:\\d+}")
	public void delete(@PathVariable String id) {
		System.out.println(id);
	}

	@GetMapping
	@JsonView(User.UserSimpleView.class)
	@ApiOperation(value = "用户查询服务")
	public List<User> query(UserQueryCondition condition,
			@PageableDefault(page = 2, size = 17, sort = "username,asc") Pageable pageable) {

		System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));

		System.out.println(pageable.getPageSize());
		System.out.println(pageable.getPageNumber());
		System.out.println(pageable.getSort());

		List<User> users = new ArrayList<>();
		users.add(new User());
		users.add(new User());
		users.add(new User());
		return users;
	}

	@GetMapping("/{id:\\d+}")
	@JsonView(User.UserDetailView.class)
	public User getInfo(@ApiParam("用户id") @PathVariable String id) {
//		throw new RuntimeException("user not exist");
		System.out.println("进入getInfo服务");
		User user = new User();
		user.setUsername("tom");
		return user;
	}

}
