package life.showlin.community.mycommunity.service.impl;

import life.showlin.community.mycommunity.mapper.TuserMapper;
import life.showlin.community.mycommunity.model.Tuser;
import life.showlin.community.mycommunity.model.TuserExample;
import life.showlin.community.mycommunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static javax.swing.UIManager.get;

/**
 * @author yyt
 * @date 2019/9/25 10:08:04
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TuserMapper userMapper;

    @Override
    public void createOrUpdate(Tuser user) {
        TuserExample tuserExample = new TuserExample();
        tuserExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<Tuser> listTuser = userMapper.selectByExample(tuserExample);
        //插入
        if (listTuser.size() == 0){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtModified());
            userMapper.insertSelective(user);
        } else {//更新
            Tuser userDB = listTuser.get(0);
            userDB.setToken(user.getToken());
            userDB.setAvatarUrl(user.getAvatarUrl());
            //userDB.setBio(user.getBio());
            userDB.setGmtModified(System.currentTimeMillis());
            userMapper.updateByExampleSelective(userDB,tuserExample);
        }
    }

    @Override
    public Tuser findByToken(String token) {
        TuserExample tuserExample = new TuserExample();
        tuserExample.createCriteria().andTokenEqualTo(token);
        List<Tuser> list = userMapper.selectByExample(tuserExample);
        if (list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public Tuser findById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

}
