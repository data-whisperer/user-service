-- Create SUPERUSER
INSERT INTO userdetails (id, fullname, email, password)
VALUES (
    gen_random_uuid(),
    'Super User',
    'superuser@example.com',
    '$2a$10$Y2hC1e9DkOXmvOATx5eP/OIuA2oF5MfefL9BA1G0Vo8yqUegABQ1S'
);

-- Add permissions
INSERT INTO user_permission (id, name, description)
VALUES 
    (gen_random_uuid(), 'CREATE_USER', 'Permission to create users'),
    (gen_random_uuid(), 'CREATE_ADMIN', 'Permission to create admin users');

-- Add access roles
INSERT INTO access_role (id, name)
VALUES 
    (gen_random_uuid(), 'USER'),
    (gen_random_uuid(), 'ADMIN');

-- Link permissions to ADMIN role
-- NOTE: You must fetch the UUIDs of the permissions and access roles inserted above
-- If you want to do it manually:
--   1. SELECT the IDs after insert
--   2. Use them in below insert
-- For demonstration, let's use CTEs to fetch IDs and connect

WITH 
    admin_role AS (
        SELECT id FROM access_role WHERE name = 'ADMIN'
    ),
    create_user_perm AS (
        SELECT id FROM user_permission WHERE name = 'CREATE_USER'
    ),
    create_admin_perm AS (
        SELECT id FROM user_permission WHERE name = 'CREATE_ADMIN'
    )
INSERT INTO accessrole2permission (access_role_id, user_permission_id)
SELECT admin_role.id, create_user_perm.id FROM admin_role, create_user_perm
UNION ALL
SELECT admin_role.id, create_admin_perm.id FROM admin_role, create_admin_perm;
