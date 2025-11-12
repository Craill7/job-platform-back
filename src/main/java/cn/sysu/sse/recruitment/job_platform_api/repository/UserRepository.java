package cn.sysu.sse.recruitment.job_platform_api.repository;

import cn.sysu.sse.recruitment.job_platform_api.domain.User;
import cn.sysu.sse.recruitment.job_platform_api.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final UserMapper userMapper;

    public UserRepository(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public int insert(User user) {
        return userMapper.insert(user);
    }

    public int update(User user) {
        return userMapper.update(user);
    }

    public int deleteById(Integer id) {
        return userMapper.deleteById(id);
    }

    public Optional<User> findById(Integer id) {
        return userMapper.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    public List<User> list(int offset, int limit) {
        return userMapper.list(offset, limit);
    }

    public long countAll() {
        return userMapper.countAll();
    }
}