# pomysly na trigger:
# 1. zmiana statusu aktywnosci uzytkownika po zalogowaniu
# 2. dodatkowa tabela przechowujaca informacje o edycji wiadomosci
# 3. dodatkowa tabela przechowujaca informacje o edycji danych konwersacji

        # CON_DEL usuwa dane konwersacji we wszystkich tabelach z kluczem obcym zanim usunie rekord z tabeli conversations
        CREATE TRIGGER CON_DEL BEFORE DELETE ON messengerdatabase.conversations
            FOR EACH ROW
            BEGIN
                DELETE FROM messengerdatabase.conversation_members
                WHERE conversation_id = old.id;
                DELETE FROM messengerdatabase.messages
                WHERE conversation_id = old.id;
                DELETE FROM messengerdatabase.moderators
                WHERE conversation_id = old.id;
            END;

        CREATE TRIGGER MOD_DEL_WHEN_USER_DEL BEFORE DELETE ON messengerdatabase.conversation_members
            FOR EACH ROW
            BEGIN
                DELETE FROM moderators
                    WHERE moderators.user_id = old.user_id and moderators.conversation_id = old.conversation_id;
            END;



