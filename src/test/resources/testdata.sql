delete from tms_rsrv_action;
delete from tms_rsrv_route;
delete from tms_rsrv_location;

-- Locations
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('1000','STCK/0001/0001/0000/0000','REC_CONV',1000,0,'316869541651',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('1001','STCK/0001/0002/0000/0000','REC_CONV',1001,0,'705831012876',false,now(),now());

-- Routes
insert into tms_rsrv_route (c_name,c_description,c_source_location,c_target_location,c_source_loc_group_name,c_target_loc_group_name, c_enabled, c_pk, c_ol, c_created, c_updated,c_pid) values ('SRC_TRG','Source -> Target',1000,1001,null,null,true,'10000',0,now(),now(),'709957655206');
insert into tms_rsrv_route (c_name,c_description,c_source_location,c_target_location,c_source_loc_group_name,c_target_loc_group_name, c_enabled, c_pk, c_ol, c_created, c_updated,c_pid) values ('REC_CONV','Cartons coming from outside to the stock.',1000,1000,'REC_CONV','FGRECEIVING', true,'10001',0,now(),now(),'709957655207');
insert into tms_rsrv_route (c_name,c_description,c_source_location,c_target_location,c_source_loc_group_name,c_target_loc_group_name, c_enabled, c_pk, c_ol, c_created, c_updated,c_pid) values ('CONVCONV','Cartons that should stay on the conveyor',1000,1000,'REC_CONV','FGCONVEYOR', true,'10002',0,now(),now(),'889085346017');
insert into tms_rsrv_route (c_name,c_description,c_source_location,c_target_location,c_source_loc_group_name,c_target_loc_group_name, c_enabled, c_pk, c_ol, c_created, c_updated,c_pid) values ('SHIPERR_','Cartons from shipping to conveyor ',null,1001,'FGSHIPPING','', true,'10003',0,now(),now(),'920761607535');
insert into tms_rsrv_route (c_name,c_description,c_source_location,c_target_location,c_source_loc_group_name,c_target_loc_group_name, c_enabled, c_pk, c_ol, c_created, c_updated,c_pid) values ('CS1_OUT_','Cartons of aisle 1 to out position',null,1001,'FGAISLE1','', true,'10004',0,now(),now(),'11168458889');
insert into tms_rsrv_route (c_name,c_description,c_source_location,c_target_location,c_source_loc_group_name,c_target_loc_group_name, c_enabled, c_pk, c_ol, c_created, c_updated,c_pid) values ('CS2_OUT_','Cartons of aisle 2 to out position',null,1001,'FGAISLE2','', true,'10006',0,now(),now(),'32391655610');
insert into tms_rsrv_route (c_name,c_description,c_source_location,c_target_location,c_source_loc_group_name,c_target_loc_group_name, c_enabled, c_pk, c_ol, c_created, c_updated,c_pid) values ('FG__ERR_','Cartons from anywhere in the flatgood area to the error location',null,1001,'FLATGOOD_AREA','', true,'10007',1000,now(),now(),'613429031593');
insert into tms_rsrv_route (c_name,c_description,c_source_location,c_target_location,c_source_loc_group_name,c_target_loc_group_name, c_enabled, c_pk, c_ol, c_created, c_updated,c_pid) values ('_NO_ROUTE','No Route',1000,null,null,null, true,'99998',0,now(),now(),'956033651143');
insert into tms_rsrv_route (c_name,c_description,c_source_location,c_target_location,c_source_loc_group_name,c_target_loc_group_name, c_enabled, c_pk, c_ol, c_created, c_updated,c_pid) values ('_DEFAULT','Route All',1000,null,null,null, true,'99999',0,now(),now(),'222590552091');

-- Actions
insert into tms_rsrv_action (c_pk,c_created,c_ol,c_pid,c_action_type,c_description,c_enabled,c_flex_1,c_flex_2,c_flex_3,c_flex_4,c_flex_5,c_location_group_name,c_name,c_program_name,c_route_id) values (1000, now(), 0, '1000', 'REQ_', 'Common action for the whole project on REQ telegrams', true, 'param1', 'param2', 'param3', 'param4', 'param5', 'ZILE', 'A0001', 'CP001', 99999) ;
insert into tms_rsrv_action (c_pk,c_created,c_ol,c_pid,c_action_type,c_description,c_enabled,c_flex_1,c_flex_2,c_flex_3,c_flex_4,c_flex_5,c_location_key,c_name,c_program_name,c_route_id) values (1001, now(), 0, '1001', 'REQ_', 'Specific action for the Stock Location on REQ telegrams', true, 'param1', 'param2', 'param3', 'param4', 'param5', 'STCK/0001/0002/0000/0000', 'A0002', 'CP002', 99998) ;