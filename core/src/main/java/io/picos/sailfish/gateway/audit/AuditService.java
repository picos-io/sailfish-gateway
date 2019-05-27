package io.picos.sailfish.gateway.audit;

public interface AuditService {

    void auditRaw(RawRequest request, Response response);

    void auditAuthn(AuthenticatedRequest request, Response response);

}
