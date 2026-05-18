
-- Insert member user
-- username: member_user
-- password: memberPw12

INSERT INTO users (
    user_name,
    password_hash,
    email,
    first_name,
    last_name,
    contributor_status
)
VALUES (
    'member_user',
    '$2a$10$3J5ZgSYcAt8IA0NhafzACOHS4MK8lGXCGX7.QI05sW3U5wOUTQB/y' ,
    'member@example.com',
    'Member',
    'test',
    'APPROVED'
)
ON CONFLICT (user_name) DO NOTHING;

-- add the role ADMIN to new super admin

INSERT INTO users_roles (user_id, role_id)
SELECT users.id, roles.id
FROM users
JOIN roles ON roles.role = 'MEMBER'
WHERE users.user_name = 'member_user'
ON CONFLICT DO NOTHING;