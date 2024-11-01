--CREATE SCHEMA flowable AUTHORIZATION sa;

-- Locations
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('181160457949','FGIN/CONV/0001/0000/0000','FGCONVEYOR','0',0,'316869541651',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('164200710058','FGIN/CONV/0002/0000/0000','FGSHIPPING','1',1,'705831012876',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('917262708951','FGIN/CONV/IN__/0001/0000','FGRECEIVING','2',2,'665107007247',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('919183566441','FGIN/CONV/OUT_/0001/0000','FGCONVEYOR','3',3,'8938711278',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('599656474784','FGIN/ERR_/0001/0000/0000','FGAUTOMATIC','4',4,'596285114197',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('266569815226','FGIN/CONV/STCK/0001/IN__','FGSTOCK','5',5,'22450287991',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('657531231826','FGIN/CONV/STCK/0001/OUT_','FGSTOCK','6',6,'138463668635',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('628639035538','FGIN/CONV/STCK/0002/IN__','FGSTOCK','7',7,'466191767491',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('758665040613','FGIN/CONV/STCK/0002/OUT_','FGSTOCK','8',8,'237171239671',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('537361305039','FGIN/0001/0000/0000/0000','FGAISLE1','9',9,'91184105028',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('801445454453','FGIN/0002/0000/0000/0000','FGAISLE2','10',10,'353286639474',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('798136030917','FGIN/0001/LIFT/0000/0000','FGAISLE1LIFT','11',11,'848093695547',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('896384899357','FGIN/0001/LEFT/0000/0000','FGAISLE1LEFT','12',12,'765225870249',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('111988110139','FGIN/0001/RGHT/0000/0000','FGAISLE1RIGHT','13',13,'566882612743',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('852306856430','FGIN/0002/LIFT/0000/0000','FGAISLE2LIFT','14',14,'439489938001',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('477193820405','FGIN/0002/LEFT/0000/0000','FGAISLE2LEFT','15',15,'772618669088',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('367699098275','FGIN/0002/RGHT/0000/0000','FGAISLE2RIGHT','16',16,'810673969392',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('819666829504','FGIN/PICK/WORK/0001/0000','FGWORKPLACE1','17',17,'13996750119',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('234175848538','FGIN/PICK/WORK/0002/0000','FGWORKPLACE2','18',18,'688928801270',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('716281042988','FGIN/PICK/WORK/0003/0000','FGWORKPLACE3','19',19,'619017918169',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('918615490790','FGIN/PICK/WORK/0004/0000','FGWORKPLACE4','20',20,'251742547073',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('722685883984','FGIN/PICK/WORK/0005/0000','FGWORKPLACE5','21',21,'803285308143',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('262863398986','FGIN/PICK/WORK/0006/0000','FGWORKPLACE6','22',22,'925770102167',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('984186159596','FGIN/PICK/WORK/0007/0000','FGWORKPLACE7','23',23,'84932885112',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('258022307027','FGIN/PICK/WORK/0008/0000','FGWORKPLACE8','24',24,'235202688583',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('542937844858','FGIN/PICK/WORK/0009/0000','FGWORKPLACE9','25',25,'771502890219',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('991930755563','KANB/0000/0000/0000/0000','FGCANBAN','26',26,'958462318751',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('408042025047','MANU/0000/0000/0000/0000','FGMANUAL','27',27,'723824617125',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('171919359409','INIT/0000/0000/0000/0000','ZILE','28',28,'929308769594',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('113646454019','EXT_/0000/0000/0000/0000','ZILE','29',29,'645065430190',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('439649733836','FGIN/IPNT/0001/0000/0000','IPOINT1','30',30,'88527472578',false,now(),now());
insert into tms_rsrv_location (c_foreign_pid,c_location_id,c_location_group_name,c_pk,c_ol,c_pid,c_marked_deletion,c_created,c_updated) values ('227916500906','FGIN/IPNT/0002/0000/0000','IPOINT2','31',31,'473074688589',false,now(),now());

-- Routes
insert into tms_rsrv_route (c_name,c_description,c_source_location,c_target_location,c_source_loc_group_name,c_target_loc_group_name, c_enabled, c_pk, c_ol, c_created, c_updated,c_pid) values ('REC_CONV','Cartons coming from outside to the stock.',null,'0','FGRECEIVING','', true,'10000',0,now(),now(),'709957655206');
insert into tms_rsrv_route (c_name,c_description,c_source_location,c_target_location,c_source_loc_group_name,c_target_loc_group_name, c_enabled, c_pk, c_ol, c_created, c_updated,c_pid) values ('CONVCONV','Cartons that should stay on the conveyor',null,'0','FGCONVEYOR','', true,'10001',0,now(),now(),'889085346017');
insert into tms_rsrv_route (c_name,c_description,c_source_location,c_target_location,c_source_loc_group_name,c_target_loc_group_name, c_enabled, c_pk, c_ol, c_created, c_updated,c_pid) values ('SHIPERR_','Cartons from shipping to conveyor ',null,'4','FGSHIPPING','', true,'10003',0,now(),now(),'920761607535');
insert into tms_rsrv_route (c_name,c_description,c_source_location,c_target_location,c_source_loc_group_name,c_target_loc_group_name, c_enabled, c_pk, c_ol, c_created, c_updated,c_pid) values ('CS1_OUT_','Cartons of aisle 1 to out position',null,'6','FGAISLE1','', true,'10004',0,now(),now(),'11168458889');
insert into tms_rsrv_route (c_name,c_description,c_source_location,c_target_location,c_source_loc_group_name,c_target_loc_group_name, c_enabled, c_pk, c_ol, c_created, c_updated,c_pid) values ('CS2_OUT_','Cartons of aisle 2 to out position',null,'8','FGAISLE2','', true,'10006',0,now(),now(),'32391655610');
insert into tms_rsrv_route (c_name,c_description,c_source_location,c_target_location,c_source_loc_group_name,c_target_loc_group_name, c_enabled, c_pk, c_ol, c_created, c_updated,c_pid) values ('FG__ERR_','Cartons from anywhere in the flatgood area to the error location',null,'4','FLATGOOD_AREA','', true,'10007',0,now(),now(),'613429031593');
insert into tms_rsrv_route (c_name,c_description,c_source_location,c_target_location,c_source_loc_group_name,c_target_loc_group_name, c_enabled, c_pk, c_ol, c_created, c_updated,c_pid) values ('_NO_ROUTE','No Route',null,null,null,null, true,'99998',0,now(),now(),'956033651143');
insert into tms_rsrv_route (c_name,c_description,c_source_location,c_target_location,c_source_loc_group_name,c_target_loc_group_name, c_enabled, c_pk, c_ol, c_created, c_updated,c_pid) values ('_DEFAULT','Route All',null,null,null,null, true,'99999',0,now(),now(),'222590552091');

-- Actions
insert into tms_rsrv_action (c_name,c_description,c_route_id,c_program_name,c_location_key,c_location_group_name,c_action_type,c_flex_1, c_enabled,c_pk,c_ol,c_created,c_updated,c_pid) values ('A0001','All without Route going to error location','99998','CP001','','ZILE','REQ_','FGIN/ERR_/0001/0000/0000',true,'10000',0,now(),now(),'709957655206');
insert into tms_rsrv_action (c_name,c_description,c_route_id,c_program_name,c_location_key,c_location_group_name,c_action_type,c_flex_1, c_enabled,c_pk,c_ol,c_created,c_updated,c_pid) values ('A0002','Handle requests coming from inbound for all routes','99999','CP001','','IPOINT','REQ_','FGIN/CONV/0001/0000/0000',true,'10001',0,now(),now(),'889085346017');
insert into tms_rsrv_action (c_name,c_description,c_route_id,c_program_name,c_location_key,c_location_group_name,c_action_type,c_flex_1, c_enabled,c_pk,c_ol,c_created,c_updated,c_pid) values ('A0003','Handle requests coming from inbound wihout route','99998','CP001','','IPOINT','REQ_','FGIN/CONV/0001/0000/0000',true,'10002',0,now(),now(),'920761607535');
