ALTER TABLE events DROP COLUMN event_type;
ALTER TABLE events DROP FOREIGN KEY events_ibfk_1;
ALTER TABLE events MODIFY column event_summary TEXT;