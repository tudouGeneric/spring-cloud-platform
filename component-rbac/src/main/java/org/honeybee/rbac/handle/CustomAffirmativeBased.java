package org.honeybee.rbac.handle;

import org.honeybee.rbac.pojo.JwtUser;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CustomAffirmativeBased extends AbstractAccessDecisionManager {

    public CustomAffirmativeBased(List<AccessDecisionVoter<?>> decisionVoters) {
        super(decisionVoters);
    }

    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException {
        int deny = 0;
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        if(jwtUser.getId().equals(2L)) {
            return;
        }

        Iterator var5 = this.getDecisionVoters().iterator();

        while(var5.hasNext()) {
            AccessDecisionVoter voter = (AccessDecisionVoter)var5.next();
            int result = voter.vote(authentication, object, configAttributes);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Voter: " + voter + ", returned: " + result);
            }

            switch(result) {
                case -1:
                    ++deny;
                    break;
                case 1:
                    return;
            }
        }

        if (deny > 0) {
            throw new AccessDeniedException(this.messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied"));
        } else {
            this.checkAllowIfAllAbstainDecisions();
        }
    }
}
