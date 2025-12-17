CREATE SCHEMA campaign_service;
CREATE USER campaign_service_user WITH PASSWORD 'campaign_service_password';
GRANT USAGE ON SCHEMA campaign_service TO campaign_service_user;
GRANT CREATE ON SCHEMA campaign_service TO campaign_service_user;
ALTER ROLE campaign_service_user SET search_path = campaign_service;

CREATE SCHEMA user_service;
CREATE USER user_service_user WITH PASSWORD 'user_service_password';
GRANT USAGE ON SCHEMA user_service TO user_service_user;
GRANT CREATE ON SCHEMA user_service TO user_service_user;
ALTER ROLE user_service_user SET search_path = user_service;

CREATE SCHEMA keycloak;
CREATE USER keycloak_user WITH PASSWORD 'keycloak_password';
GRANT USAGE ON SCHEMA keycloak TO keycloak_user;
GRANT CREATE ON SCHEMA keycloak TO keycloak_user;
ALTER ROLE keycloak_user SET search_path = keycloak;

