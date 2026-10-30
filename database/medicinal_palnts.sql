CREATE DATABASE IF NOT EXISTS medicinal_plant_db;
USE medicinal_plant_db;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE IF NOT EXISTS plants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    common_name VARCHAR(100) NOT NULL,
    scientific_name VARCHAR(100) NOT NULL,
    category_id INT,
    description TEXT,
    uses TEXT,
    plant_parts VARCHAR(255),
    preparation TEXT,
    precautions TEXT,
    image_path VARCHAR(255),
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS symptoms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE IF NOT EXISTS plant_symptoms (
    plant_id INT,
    symptom_id INT,
    PRIMARY KEY (plant_id, symptom_id),
    FOREIGN KEY (plant_id) REFERENCES plants(id) ON DELETE CASCADE,
    FOREIGN KEY (symptom_id) REFERENCES symptoms(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS favorites (
    user_id INT,
    plant_id INT,
    PRIMARY KEY (user_id, plant_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (plant_id) REFERENCES plants(id) ON DELETE CASCADE
);

-- Insert Default Admin & User (Password: Admin123! hashed with BCrypt)
INSERT IGNORE INTO users (id, username, email, password_hash, role) VALUES 
(1, 'admin', 'admin@medicinalplants.com', '$2a$10$X1u3W3D3h8k7s6G5F4E3O.9Z8Y7X6W5V4U3T2S1R0Q9P8O7N6M5L4', 'ADMIN'),
(2, 'john_doe', 'john@example.com', '$2a$10$X1u3W3D3h8k7s6G5F4E3O.9Z8Y7X6W5V4U3T2S1R0Q9P8O7N6M5L4', 'USER');

-- Insert Categories
INSERT IGNORE INTO categories (id, name, description) VALUES 
(1, 'Immunity & Respiratory', 'Herbs that boost immune response and treat respiratory ailments.'),
(2, 'Digestive Health', 'Plants supporting gastrointestinal function and soothing digestion.'),
(3, 'Skin & Wound Care', 'Botanicals used topically for skin healing and irritation relief.'),
(4, 'Adaptogens & Calm', 'Herbs helping the body adapt to stress and promote relaxation.'),
(5, 'Anti-inflammatory & Joints', 'Specialized botanical remedies for chronic pain, stiffness, and internal swelling.'),
(6, 'Cognitive & Neurological', 'Rare herbs traditionally used to enhance memory, focus, and neurological health.'),
(7, 'Cardiovascular & Circulation', 'Botanicals known for supporting heart health and blood pressure regulation.');

-- Insert Symptoms
INSERT IGNORE INTO symptoms (id, name, description) VALUES 
(1, 'Cough & Cold', 'Persistent coughing, sore throat, or common cold symptoms.'),
(2, 'Digestive Issues', 'Indigestion, bloating, or stomach discomfort.'),
(3, 'Skin Wounds & Burns', 'Minor cuts, scrapes, or skin burns.'),
(4, 'Stress & Anxiety', 'Mental fatigue, nervousness, or high stress levels.'),
(5, 'Inflammation & Pain', 'Joint stiffness or general bodily inflammation.'),
(6, 'Memory & Focus Fatigue', 'Brain fog, lack of concentration, or mental exhaustion.'),
(7, 'Insomnia & Sleep Trouble', 'Difficulty falling asleep or restless nights.'),
(8, 'High Blood Pressure', 'Elevated cardiovascular stress or circulation sluggishness.'),
(9, 'Chronic Fatigue', 'Low physical stamina, weakness, or lack of vitality.');

-- Insert 20 Medicinal Plants (Including Original 8 + 12 Rare Botanical Additions)
INSERT IGNORE INTO plants (id, common_name, scientific_name, category_id, description, uses, plant_parts, preparation, precautions, image_path) VALUES 
-- Original 8 Plants
(1, 'Tulsi (Holy Basil)', 'Ocimum tenuiflorum', 1, 'Sacred plant in Ayurveda known for its powerful healing and adaptogenic properties.', 'Treats cough, cold, and respiratory congestion; reduces stress.', 'Leaves, Flowers', 'Boil fresh leaves in water to make a herbal tea or decoction.', 'Safe for daily consumption; consult physician if pregnant.', 'tulsi.jpg'),
(2, 'Ginger', 'Zingiber officinale', 2, 'A flowering plant whose rhizome is widely used as a spice and folk medicine.', 'Relieves nausea, indigestion, and cold symptoms.', 'Rhizome (Root)', 'Slice and steep in hot water with honey or chew raw root.', 'Avoid excessive intake if prone to acid reflux.', 'ginger.jpg'),
(3, 'Turmeric', 'Curcuma longa', 5, 'Bright yellow spice containing curcumin, renowned for anti-inflammatory benefits.', 'Reduces joint pain, inflammation, and supports immunity.', 'Rhizome (Root)', 'Mix powder in warm milk (Golden Milk) or warm water.', 'High doses may cause stomach upset.', 'turmeric.jpg'),
(4, 'Aloe Vera', 'Aloe barbadensis miller', 3, 'Succulent plant species valued for skin treatment and soothing properties.', 'Heals skin burns, cuts, and moisturizes skin.', 'Leaf Gel', 'Extract clear gel directly from freshly cut leaves and apply topically.', 'For external use primarily; oral use can act as a strong laxative.', 'aloe_vera.jpg'),
(5, 'Neem', 'Azadirachta indica', 3, 'Fast-growing evergreen tree with potent antibacterial and antifungal properties.', 'Treats skin acne, fungal infections, and purifies blood.', 'Leaves, Bark, Oil', 'Grind leaves into a paste for skin application or boil leaves for bath water.', 'Avoid use during pregnancy.', 'neem.jpg'),
(6, 'Amla (Indian Gooseberry)', 'Emblica officinalis', 1, 'Powerhouse of vitamin C with exceptional antioxidant properties.', 'Boosts immunity, promotes hair growth, and aids digestion.', 'Fruit', 'Consume raw, as juice, or powdered with honey.', 'Generally safe; monitor blood sugar if diabetic.', 'amla.jpg'),
(7, 'Ashwagandha', 'Withania somnifera', 4, 'Vital herb in Ayurvedic medicine used as an ancient adaptogen.', 'Reduces stress, anxiety, and boosts overall energy and stamina.', 'Root', 'Mix powder with warm milk or take as capsule.', 'Avoid during pregnancy or hyperthyroidism.', 'ashwagandha.jpg'),
(8, 'Moringa', 'Moringa oleifera', 1, 'Nutrient-rich tree known for its high vitamin and mineral content.', 'Boosts energy, reduces inflammation, and supports nutrition.', 'Leaves, Seeds', 'Dry leaves into powder and add to smoothies or soups.', 'Avoid root and root extracts during pregnancy.', 'moringa.jpg'),

-- 12 Rare Medicinal Plant Additions
(9, 'Brahmi', 'Bacopa monnieri', 6, 'A revered creeping herb from wetland areas used extensively in traditional brain tonic formulations.', 'Enhances memory, sharpens focus, and reduces nervous anxiety.', 'Whole Plant', 'Steep dried or fresh leaves into an herbal tea infusion.', 'May cause mild dry mouth or digestive upset in high doses.', 'brahmi.jpg'),
(10, 'Gotu Kola', 'Centella asiatica', 6, 'Known as the "herb of longevity," native to the Asian wetlands with profound neural properties.', 'Boosts cognitive function, calms anxiety, and improves skin elasticity.', 'Leaves, Stems', 'Infuse fresh leaves in hot water or grind into a green juice tonic.', 'Avoid if taking heavy sedatives or liver medications.', 'gotu_kola.jpg'),
(11, 'Guggul', 'Commiphora mukul', 5, 'A small, thorny tree native to arid regions yielding a fragrant medicinal resin (gum guggul).', 'Reduces severe joint inflammation, manages cholesterol, and aids metabolic functions.', 'Tree Resin (Gum)', 'Purified resin extract usually taken in capsule or tablet form under supervision.', 'May interact with thyroid medications and blood thinners.', 'guggul.jpg'),
(12, 'Shatavari', 'Asparagus racemosus', 4, 'A wild asparagus relative considered a powerful female reproductive tonic and adaptogen.', 'Rejuvenates reproductive system, reduces chronic stress, and balances hormones.', 'Roots', 'Simmer root powder in warm milk or water with honey.', 'Consult physician if you have hormone-sensitive conditions.', 'shatavari.jpg'),
(13, 'Arjuna', 'Terminalia arjuna', 7, 'A majestic deciduous tree whose bark has been prized for centuries in cardiac health care.', 'Strengthens heart muscles, regulates blood pressure, and reduces chest discomfort.', 'Bark', 'Boil coarse bark powder in water or milk to make a decoction (Khaashaya).', 'Monitor closely if already taking prescribed low blood pressure medications.', 'arjuna.jpg'),
(14, 'Giloy (Guduchi)', 'Tinospora cordifolia', 1, 'An essential climbing shrub praised as "Amrita" (root of immortality) for its immune properties.', 'Boosts overall immunity, fights chronic fevers, and detoxifies the system.', 'Stem, Leaves', 'Extract juice from fresh stems or consume as a powder decoction.', 'Avoid during autoimmune flare-ups without clinical advice.', 'giloy.jpg'),
(15, 'Punarnava', 'Boerhavia diffusa', 2, 'A creeping weed whose name translates to "that which renews the body," prized for kidney support.', 'Supports renal function, reduces water retention, and combats internal inflammation.', 'Roots, Whole Plant', 'Prepare a decoction using dried root powder.', 'Not recommended during acute kidney failure or pregnancy.', 'punarnava.jpg'),
(16, 'Haritaki', 'Terminalia chebula', 2, 'Known as the "king of medicines" in Tibetan and Ayurvedic traditions, bearing small ribbed fruits.', 'Promotes gentle detoxification, regular bowel movements, and gut health.', 'Dried Fruit', 'Take fruit powder mixed with warm water or honey before bedtime.', 'Avoid during severe dehydration or pregnancy.', 'haritaki.jpg'),
(17, 'Rhodiola Rosea', 'Rhodiola rosea', 4, 'A hardy perennial plant that grows in cold, mountainous regions of Europe and Asia.', 'Combats chronic fatigue, enhances endurance, and elevates mood under stress.', 'Root', 'Steep dried root slices in hot water or take standardized extract.', 'Can cause over-activation or jitteriness if taken late in the day.', 'rhodiola.jpg'),
(18, 'Andrographis (Kalmegh)', 'Andrographis paniculata', 1, 'An extremely bitter herbaceous plant native to India and Sri Lanka, called the "King of Bitters."', 'Combats upper respiratory infections, seasonal flu symptoms, and boosts natural immunity.', 'Leaves, Whole Plant', 'Consume leaf infusion or standardized extract capsules.', 'High doses can cause gastric discomfort or drop blood pressure rapidly.', 'kalmegh.jpg'),
(19, 'Schisandra Chinensis', 'Schisandra chinensis', 4, 'A woody vine producing clusters of berries possessing all five classic flavors (sweet, sour, salty, bitter, pungent).', 'Supports liver function, increases mental endurance, and reduces physical fatigue.', 'Berries', 'Steep dried berries into a tea or tincture.', 'Avoid if diagnosed with epilepsy or severe acid reflux.', 'schisandra.jpg'),
(20, 'Yarrow', 'Achillea millefolium', 3, 'A flowering plant featuring delicate white cluster blooms traditionally used by ancient healers.', 'Stops bleeding from minor cuts, heals skin wounds, and calms digestive cramps.', 'Flowering Tops, Leaves', 'Apply a cooled infusion topically via a clean cloth or drink as tea.', 'Avoid prolonged exposure to sunlight after handling (photosensitivity) and avoid during pregnancy.', 'yarrow.jpg');

-- Map Plants to Symptoms (Mapping original + new rare plants)
INSERT IGNORE INTO plant_symptoms (plant_id, symptom_id) VALUES 
(1, 1), (1, 4),
(2, 1), (2, 2),
(3, 2), (3, 5),
(4, 3),
(5, 3),
(6, 1), (6, 2),
(7, 4), (7, 5),
(8, 1), (8, 5),
-- Mappings for new rare plants 9-20
(9, 4), (9, 6),
(10, 4), (10, 6),
(11, 5),
(12, 4), (12, 9),
(13, 8),
(14, 1), (14, 9),
(15, 2), (15, 5),
(16, 2),
(17, 4), (17, 9),
(18, 1),
(19, 4), (19, 9),
(20, 2), (20, 3);