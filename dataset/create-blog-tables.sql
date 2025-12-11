CREATE TABLE authors (
                         id UUID PRIMARY KEY,
                         username VARCHAR(255) UNIQUE NOT NULL,
                         email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE articles (
                         id UUID PRIMARY KEY,
                         title VARCHAR(255) UNIQUE NOT NULL,
                         content TEXT,
                         publish_date TIMESTAMP,
                         is_published BOOLEAN DEFAULT FALSE,
                         author_id UUID REFERENCES authors(id)
);

CREATE TABLE comments (
                          id UUID PRIMARY KEY,
                          author_name VARCHAR(255) NOT NULL,
                          author_email VARCHAR(255) NOT NULL,
                          content TEXT,
                          comment_date TIMESTAMP,
                          article_id UUID REFERENCES articles(id)
);

-- Authors
INSERT INTO authors (id, username, email) VALUES
                                              ('40e6215d-b5c6-4896-987c-f30f3678f608', 'alice', 'alice@example.com'),
                                              ('6ecd8c99-4036-403d-bf84-cf8400f67836', 'bob', 'bob@example.com'),
                                              ('d0216c95-a141-404a-9e56-b80f05936812', 'carla', 'carla@example.com'),
                                              ('a60b3bfe-d596-40c9-b296-2bd1987bc076', 'dan', 'dan@example.com');

-- Articles
INSERT INTO articles (id, title, content, publish_date, is_published, author_id) VALUES
                                                                                     ('fa730898-3587-4809-b22c-f84b34b26cfe', 'The Rise of AI Technologies','Artificial intelligence is transforming industries across the world...', '2024-01-10 09:00:00', TRUE, '40e6215d-b5c6-4896-987c-f30f3678f608'),
                                                                                     ('096249c1-d1d1-4d92-8632-bda1e627d111','Understanding Cloud Computing','Cloud computing allows businesses to scale infrastructure efficiently...', '2024-03-15 14:30:00', TRUE, '40e6215d-b5c6-4896-987c-f30f3678f608'),
                                                                                     ('42af0ee7-9a81-4399-b775-72cb2a749da8','Top 10 Programming Languages in 2025','The tech landscape evolves quickly. Here are the most popular languages...', NULL, FALSE, '6ecd8c99-4036-403d-bf84-cf8400f67836'),
                                                                                     ('a200870b-6f83-4d3e-a274-a1591f2f4fcf','How Blockchain Works','Blockchain is a decentralized ledger technology...', '2024-05-01 08:15:00', TRUE, 'd0216c95-a141-404a-9e56-b80f05936812');

-- Comments
INSERT INTO comments (id, author_name, author_email, content, comment_date, article_id) VALUES
                                                                                            ('78884f5c-5af0-4921-9dd4-cb7687693dd9', 'Jean', 'jean@mail.com', 'Great insights on AI!', '2024-01-11 10:05:00', 'fa730898-3587-4809-b22c-f84b34b26cfe'),
                                                                                            ('807139db-d2e5-4dd2-acdf-31fe2474f3c1', 'Marie', 'marie@mail.com',  'Cloud computing still confuses me, but this helped.', '2024-03-16 09:00:00', 'fa730898-3587-4809-b22c-f84b34b26cfe'),
                                                                                            ('73204bf2-e65f-4f72-8e2b-541881873d3e', 'Luc', 'luc@mail.com', 'This article provides a clear and accessible overview of cloud computing, making a complex topic much easier to understand.', '2024-04-01 12:00:00', '096249c1-d1d1-4d92-8632-bda1e627d111'),
                                                                                            ('6ef3aa9e-69b4-4572-9744-840ed7a45c38', 'Sophie', 'sophie@mail.com', 'Great overview—Understanding Cloud Computing really clarifies how virtualized resources make modern computing more flexible and scalable.', '2024-05-02 07:30:00', '096249c1-d1d1-4d92-8632-bda1e627d111'),
                                                                                            ('10ae62ad-37fd-4dee-b4c7-4c236b5f3e17', 'Paul', 'paul@mail.com',  'Rust should definitely be higher on the list!', '2024-01-12 15:20:00', '42af0ee7-9a81-4399-b775-72cb2a749da8'),
                                                                                            ('4a26bf44-5fae-4960-ac49-eb23d149e76f', 'Nora', 'nora@mail.com', 'Interesting to see how emerging languages are climbing the ranks in 2025’’s top 10 list.', '2024-02-21 13:40:00', '42af0ee7-9a81-4399-b775-72cb2a749da8');

