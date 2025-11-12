package cn.sysu.sse.recruitment.job_platform_api.server.security;

import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 自定义用户详情实现
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
	private User user;

	/**
	 * 获取用户权限
	 * @return 权限集合
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String roleName = switch (user.getRole()) {
			case STUDENT -> "ROLE_STUDENT";
			case HR -> "ROLE_HR";
		};
		return List.of(new SimpleGrantedAuthority(roleName));
	}

	/**
	 * 获取用户密码
	 * @return 用户密码哈希
	 */
	@Override
	public String getPassword() {
		return user.getPasswordHash();
	}

	/**
	 * 获取用户名
	 * @return 用户ID字符串
	 */
	@Override
	public String getUsername() {
		return String.valueOf(user.getId());
	}

	/**
	 * 账户是否未过期
	 * @return true（账户永不过期）
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 账户是否未锁定
	 * @return true（账户永不锁定）
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * 凭证是否未过期
	 * @return true（凭证永不过期）
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 账户是否启用
	 * @return 账户是否处于激活状态
	 */
	@Override
	public boolean isEnabled() {
		return user.getStatus() != null && user.getStatus().getCode() == 1; // ACTIVE=1
	}
}


