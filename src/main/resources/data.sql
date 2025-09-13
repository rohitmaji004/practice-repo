-- Third-Party Liability Policies
INSERT INTO policy (policy_name, policy_type, insured_declared_value, premium_amount, description, start_date, end_date) VALUES
('Basic Third-Party Cover', 'THIRD_PARTY_LIABILITY', 500000.00, 2500.00, 'Legally mandatory cover for damages to a third party.', '2024-01-01', '2025-01-01'),
('Essential TP Shield', 'THIRD_PARTY_LIABILITY', 750000.00, 3500.00, 'Enhanced third-party liability coverage.', '2024-01-01', '2025-01-01');

-- Standalone Own-Damage Policies
INSERT INTO policy (policy_name, policy_type, insured_declared_value, premium_amount, description, start_date, end_date) VALUES
('My Vehicle Protector', 'STANDALONE_OWN_DAMAGE', 600000.00, 8000.00, 'Covers damages to your own vehicle from accidents or theft.', '2024-01-01', '2025-01-01'),
('OD Secure Plan', 'STANDALONE_OWN_DAMAGE', 850000.00, 10000.00, 'A comprehensive own-damage policy with higher IDV.', '2024-01-01', '2025-01-01');

-- Comprehensive Policies
INSERT INTO policy (policy_name, policy_type, insured_declared_value, premium_amount, description, start_date, end_date) VALUES
('Comprehensive Gold Plan', 'COMPREHENSIVE', 1000000.00, 15000.00, 'Complete protection for your vehicle, including own-damage and third-party liability.', '2024-01-01', '2025-01-01'),
('All-in-One Platinum', 'COMPREHENSIVE', 1500000.00, 22000.00, 'Our most extensive policy, offering the highest level of protection and benefits.', '2024-01-01', '2025-01-01');

-- Add-ons for Comprehensive Gold Plan (Policy ID 5)
INSERT INTO policy_addons (policy_id, addons) VALUES
(5, 'ZERO_DEPRECIATION'),
(5, 'ROADSIDE_ASSISTANCE'),
(5, 'NO_CLAIM_BONUS_PROTECTION');

-- Add-ons for All-in-One Platinum (Policy ID 6)
INSERT INTO policy_addons (policy_id, addons) VALUES
(6, 'ZERO_DEPRECIATION'),
(6, 'ENGINE_PROTECTION'),
(6, 'ROADSIDE_ASSISTANCE'),
(6, 'RETURN_TO_INVOICE'),
(6, 'NO_CLAIM_BONUS_PROTECTION');