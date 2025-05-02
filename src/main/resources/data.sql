insert into Ingredient (id, name, type)
    values
    ('GRN', 'Grain', 'WRAP'),
    ('RYE', 'Rye', 'WRAP'),
    ('PRK', 'Pork', 'PROTEIN'),
    ('BEF', 'Beef', 'PROTEIN'),
    ('CHN', 'Chicken', 'PROTEIN'),
    ('CHR', 'Cheddar', 'CHEESE'),
    ('PSN', 'Parmesan', 'CHEESE'),
    ('TMS', 'Tomatoes', 'VEGETABLE'),
    ('CBG', 'Cabbage', 'VEGETABLE'),
    ('ONI', 'Onion', 'VEGETABLE'),
    ('CHS', 'Cheesy', 'SAUCE'),
    ('KUP', 'Ketchup', 'SAUCE')
    ON CONFLICT (id) DO NOTHING;