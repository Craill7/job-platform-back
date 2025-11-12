package cn.sysu.sse.recruitment.job_platform_api.server.security;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.User;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 自定义用户详情服务
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private UserMapper userMapper;

	/**
	 * 根据用户名加载用户详情
	 * @param username 用户名（邮箱）
	 * @return 用户详情
	 * @throws UsernameNotFoundException 用户未找到异常
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("开始加载用户详情，用户名：{}", username);
		// username 在登录时将使用 email；在 JWT 中我们也存 email 作为主体
		User user = userMapper.findByEmail(username)
				.orElseThrow(() -> {
					logger.warn("用户未找到，用户名：{}", username);
					return new UsernameNotFoundException("User not found");
				});
		logger.debug("用户详情加载成功，用户名：{}，用户ID：{}", username, user.getId());
		return new CustomUserDetails(user);
	}
}


