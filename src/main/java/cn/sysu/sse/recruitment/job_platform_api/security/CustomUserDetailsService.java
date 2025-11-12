package cn.sysu.sse.recruitment.job_platform_api.security;

import cn.sysu.sse.recruitment.job_platform_api.domain.User;
import cn.sysu.sse.recruitment.job_platform_api.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserMapper userMapper;

	public CustomUserDetailsService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// username 在登录时将使用 email；在 JWT 中我们也存 email 作为主体
		User user = userMapper.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new CustomUserDetails(user);
	}
}


