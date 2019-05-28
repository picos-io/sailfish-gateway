package io.picos.sailfish.gateway.impl.audit;

import io.picos.sailfish.gateway.audit.AuditService;
import io.picos.sailfish.gateway.audit.AuthenticatedRequest;
import io.picos.sailfish.gateway.audit.RawRequest;
import io.picos.sailfish.gateway.audit.Response;
import io.picos.sailfish.gateway.impl.audit.model.GatewayActivity;
import io.picos.sailfish.gateway.impl.audit.repository.GatewayActivityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class AuditServiceImpl implements AuditService {

    @Autowired
    private GatewayActivityRepository gatewayRequestRepository;

    @Override
    public void auditRaw(RawRequest request, Response response) {
        GatewayActivity gatewayRequest = new GatewayActivity();
        BeanUtils.copyProperties(request, gatewayRequest);

        gatewayRequest.setHttpStatus(response.getHttpStatus());
        gatewayRequest.setResponseBody(response.getBody());

        gatewayRequestRepository.save(gatewayRequest);
    }

    @Override
    public void auditAuthn(AuthenticatedRequest request, Response response) {
        GatewayActivity gatewayRequest = new GatewayActivity();
        BeanUtils.copyProperties(request, gatewayRequest);

        gatewayRequest.setRequestById(request.getUserId());
        gatewayRequest.setRequestByName(request.getUsername());

        gatewayRequest.setHttpStatus(response.getHttpStatus());
        gatewayRequest.setResponseBody(response.getBody());

        gatewayRequestRepository.save(gatewayRequest);
    }

}
