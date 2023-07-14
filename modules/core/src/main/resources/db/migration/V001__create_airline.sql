create table airline (
                        id          int not null primary key,
                        name        varchar(100) not null,
                        alias       varchar(100),
                        iata_id     varchar(2) not null,
                        icao_id     varchar(3) not null,
                        call_sign   varchar(100) not null,
                        country     varchar(100) not null,
                        active      boolean not null
);

INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (1,'Private flight',null,'-','N/A','','',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (130,'Aeroflot Russian Airlines',null,'SU','AFL','AEROFLOT','Russia',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (503,'Aeroflot-Nord',null,'5N','AUL','DVINA','Russia',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (816,'Aeroflot-Don',null,'D9','DNV','DONAVIA','Russia',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (1178,'Aeroflot-Cargo',null,'','RCF','AEROFLOT-CARGO','Russia',false);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (412,'Aerolineas Argentinas',null,'AR','ARG','ARGENTINA','Argentina',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (321,'AeroMéxico',null,'AM','AMX','AEROMEXICO','Mexico',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (90,'Air Europa',null,'UX','AEA','EUROPA','Spain',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (137,'Air France',null,'AF','AFR','AIRFRANS','France',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (596,'Alitalia',null,'AZ','AZA','ALITALIA','Italy',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (16975,'Alitalia Cityliner','','CT','CYL','','Italy',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (371,'Alitalia Express',null,'XM','SMX','ALIEXPRESS','Italy',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (1756,'China Airlines',null,'CI','CAL','DYNASTY','Taiwan',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (1758,'China Eastern Airlines',null,'MU','CES','CHINA EASTERN','China',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (1767,'China Southern Airlines',null,'CZ','CSN','CHINA SOUTHERN','China',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (1946,'Czech Airlines','CSA Czech Airlines','OK','CSA','CSA-LINES','Czech Republic',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (2007,'Delta Air Charter',null,'','SNO','SNOWBALL','Canada',false);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (2008,'Delta Air Elite',null,'','ELJ','ELITE JET','United States',false);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (2009,'Delta Air Lines',null,'DL','DAL','DELTA','United States',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (2520,'Garuda Indonesia',null,'GA','GIA','INDONESIA','Indonesia',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (3126,'Kenya Airways',null,'KQ','KQA','KENYA','Kenya',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (3088,'KLM Cityhopper',null,'WA','KLC','CITY','Netherlands',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (3089,'KLM Helicopter',null,'','KLH','KLM HELI','Netherlands',false);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (3090,'KLM Royal Dutch Airlines',null,'KL','KLM','KLM','Netherlands',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (3163,'Korean Air',null,'KE','KAL','KOREANAIR','Republic of Korea',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (3490,'Middle East Airlines',null,'ME','MEA','CEDAR JET','Lebanon',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (4533,'Saudi Arabian Airlines',null,'SV','SVA','SAUDIA','Saudi Arabia',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (5179,'Tarom',null,'RO','ROT','TAROM','Romania',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (5309,'Vietnam Airlines',null,'VN','HVN','VIET NAM AIRLINES','Vietnam',true);
INSERT INTO airline (id, name, alias, iata_id, icao_id, call_sign, country, active) values (5484,'Xiamen Airlines',null,'MF','CXA','XIAMEN AIR','China',true);


CREATE SEQUENCE airline_seq START 6000;
