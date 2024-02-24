CREATE DATABASE messengerdatabase CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE messages(id INT unsigned NOT NULL PRIMARY KEY auto_increment,
						conversation_id INT unsigned NOT NULL,
						user_id INT unsigned NOT NULL,
                        content VARCHAR(1024),
                        time_of_writing datetime,
                        answer_to_id INT unsigned);
CREATE TABLE users(id INT unsigned not null primary key auto_increment,
					first_name varchar(32),
                    last_name varchar(32),
                    avatar mediumblob,
                    status enum('not active', 'active') not null,
                    is_deleted boolean not null,
                    is_admin boolean);
CREATE TABLE conversations(id int unsigned not null primary key auto_increment,
							name varchar(32) unique,
                            creation_date datetime,
                            invitation bool,
                            avatar mediumblob,
                            admin_id int unsigned);
CREATE TABLE conversation_members(user_id int unsigned not null,
									conversation_id int unsigned not null);
CREATE TABLE moderators(user_id int unsigned not null,
						conversation_id int unsigned not null);
CREATE TABLE interactions(id int unsigned not null primary key auto_increment,
							user_id int unsigned not null,
                            type_of_interaction int,
                            message_id int unsigned);


ALTER TABLE messages ADD constraint FK_conversation_id foreign key (conversation_id)
										references conversations(id) on delete cascade ;
ALTER TABLE messages ADD constraint FK_user_id foreign key (user_id)
										references users(id);

ALTER TABLE conversation_members ADD constraint FK_cm_user_id foreign key (user_id)
										references users(id);
ALTER TABLE conversation_members ADD constraint FK_cm_conversation_id foreign key (conversation_id)
										references conversations(id) on delete cascade;

ALTER TABLE interactions ADD constraint FK_i_user_id foreign key (user_id)
								references users(id);

ALTER TABLE interactions ADD constraint FK_i_message_id foreign key (message_id)
								references messages(id) on delete cascade;

ALTER TABLE moderators ADD constraint FK_m_user_id foreign key (user_id)
                                references users(id);
ALTER TABLE moderators ADD constraint FK_m_conversation_id foreign key (conversation_id)
                                references conversations(id) on delete cascade;

