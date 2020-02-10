package life.showlin.community.mycommunity.service;

import life.showlin.community.mycommunity.model.Tuser;

/**
 * @author yyt
 * @date 2019/9/25 10:07:34
 * @description
 */
public interface UserService {
    void createOrUpdate(Tuser user);

    Tuser findByToken(String token);

    Tuser findById(Long id);


}
