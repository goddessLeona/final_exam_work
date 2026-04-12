
-- Insert super admin user
-- username: super_admin
-- password: Admin123

INSERT INTO users (
    user_name,
    password_hash,
    email,
    first_name,
    last_name,
    is_contributor
)
VALUES (
    'super_admin',
    '$2a$10$v73EcAli7LetpRBsh7nd4epqetbpQRsq5toEh3TR96z.uPRANlvC2' ,
    'admin@example.com',
    'Super',
    'Admin',
    false
)
ON CONFLICT (user_name) DO NOTHING;

-- add the role ADMIN to new super admin

INSERT INTO users_roles (user_id, role_id)
SELECT users.id, roles.id
FROM users
JOIN roles ON roles.role = 'ADMIN'
WHERE users.user_name = 'super_admin'
ON CONFLICT DO NOTHING;




