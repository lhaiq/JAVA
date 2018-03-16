//		package com.hengsu.bhyy.core.service;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNull;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.wlw.pylon.unit.BaseDbTest;
//import com.hengsu.bhyy.core.service.DoctorService;
//import com.hengsu.bhyy.core.model.DoctorModel;
//
//import java.util.Date;
//
//public class DoctorServiceTest extends BaseDbTest{
//
//	@Autowired
//	private DoctorService doctorService;
//
//	@Test
//	public void testCreate() throws Exception {
//		DoctorModel doctorModel = new DoctorModel();
//		doctorModel.setDoctorId("dd8afab6-a763-469e-bcf8-431dca86");
//		doctorModel.setRealName("541eb21b-24da-4f22-a11d-2cb4f30cd345");
//		doctorModel.setPhone("9b2adadf-1eb4-4e37-895b-9116fa17");
//		doctorModel.setIcon("2b096d86-33dd-401e-8909-3505303e81e3");
//		doctorModel.setGender(52142349);
//		doctorModel.setBirthday(new Date());
//		doctorModel.setHospitalName("f55ba8a9-d68f-4360-bfea-aa15fa63");
//		doctorModel.setDepartment(47087782);
//		doctorModel.setTitle("80f17d70-87d2-4239-b378-dc8c3d1f");
//		doctorModel.setAdept("b64db1df-d5b8-493f-908e-fde3d359320e");
//		doctorModel.setEducation("e8b66144-dd9f-4329-9316-ea3d2906");
//		doctorModel.setWorkYear(94238800);
//		doctorModel.setIntro("5031688d-ad3c-4c97-991b-7086ede2c17a");
//		doctorModel.setIdIcon("47e0814d-b394-4a1c-a495-a8fa43ab955e");
//		doctorModel.setJobIcon("0638be31-4106-4df0-a544-34bbbdcd8046");
//		doctorModel.setDoctorIcon("e6ab625f-4cce-47aa-9142-1f127215749b");
//		doctorModel.setStatus(64212046);
//		doctorModel.setAge(47674436);
//		doctorModel.setServiceItem(83952388);
//		doctorModel.setIsShow(38932040);
//		doctorModel.setAssistantIcon("1b906ea4-8f08-4bce-8387-751c959c0a07");
//		doctorModel.setSource(35359947);
//		doctorModel.setAddDate(new Date());
//		doctorModel.setFailureReason("585f2721-406c-4134-bfed-3d616a792cb5");
//		String pkValue = doctorModel.getDoctorId();
//		saveModel(doctorModel);
//
//		DoctorModel findModel = doctorService.findByPrimaryKey(pkValue);
//		assertEquals(pkValue, findModel.getDoctorId());
//		assertEquals(doctorModel.getRealName(), findModel.getRealName());
//
//		cleanModel(pkValue);
//	}
//
//	@Test
//	public void testFindByPrimaryKey() throws Exception {
//		DoctorModel doctorModel = new DoctorModel();
//		doctorModel.setDoctorId("8e729251-1188-463b-a595-a8c8459d");
//		doctorModel.setRealName("03562840-2eea-4ba9-8530-15255e4d2590");
//		doctorModel.setPhone("286092fe-132b-4223-b17a-c2c06658");
//		doctorModel.setIcon("55b80b1c-c498-4c39-b124-aa8beefd5dcd");
//		doctorModel.setGender(26747919);
//		doctorModel.setBirthday(new Date());
//		doctorModel.setHospitalName("bbfa1dac-5f7b-4b88-8bc6-69bba441");
//		doctorModel.setDepartment(54305459);
//		doctorModel.setTitle("fd362a73-5554-435b-bd84-157faea5");
//		doctorModel.setAdept("20ea4339-0ba5-47f5-a489-5873fc8506cf");
//		doctorModel.setEducation("2ad981a1-bdb4-43c9-91d2-b0ade206");
//		doctorModel.setWorkYear(34781442);
//		doctorModel.setIntro("a3d4186b-17bf-43f7-88e4-19a42869072f");
//		doctorModel.setIdIcon("fdaf6165-8032-4e06-81d3-d8d1cf551349");
//		doctorModel.setJobIcon("4f97506e-3703-4e8c-a265-cda88315c61a");
//		doctorModel.setDoctorIcon("19610269-02b9-4641-940d-5301f4dde901");
//		doctorModel.setStatus(78994790);
//		doctorModel.setAge(53601502);
//		doctorModel.setServiceItem(47209561);
//		doctorModel.setIsShow(67895075);
//		doctorModel.setAssistantIcon("d88a6f3e-3cf1-4004-93d4-3a377aaac33a");
//		doctorModel.setSource(43045900);
//		doctorModel.setAddDate(new Date());
//		doctorModel.setFailureReason("edfeb597-f0fb-49d2-b09d-824e93e3c884");
//		String pkValue = doctorModel.getDoctorId();
//		saveModel(doctorModel);
//
//		DoctorModel findModel = doctorService.findByPrimaryKey(pkValue);
//		assertEquals(pkValue, findModel.getDoctorId());
//		assertEquals(doctorModel.getRealName(), findModel.getRealName());
//
//		cleanModel(pkValue);
//	}
//
//	@Test
//	public void testUpdateByPrimaryKey() throws Exception {
//		DoctorModel doctorModel = new DoctorModel();
//		doctorModel.setDoctorId("d13cb68c-a5de-4754-91fb-847ea5a5");
//		doctorModel.setRealName("628083e0-08ae-455b-8807-9d06d3e61141");
//		doctorModel.setPhone("2ec7bfd5-56aa-42ec-a610-05c910f7");
//		doctorModel.setIcon("574c0692-4e11-4b47-aa0e-f58403cfa703");
//		doctorModel.setGender(23969472);
//		doctorModel.setBirthday(new Date());
//		doctorModel.setHospitalName("fff860c8-fe50-424f-9ea5-7177db9d");
//		doctorModel.setDepartment(76271089);
//		doctorModel.setTitle("86360f5b-1c27-4778-a268-576aa86d");
//		doctorModel.setAdept("a7b1af8d-b942-4eb7-a950-c17e8c035bae");
//		doctorModel.setEducation("591a4519-b35f-4946-87f6-ea4293d1");
//		doctorModel.setWorkYear(95858902);
//		doctorModel.setIntro("5e582ac5-9005-48f5-80dc-a0453821ac80");
//		doctorModel.setIdIcon("e9ec4ffb-c89e-45a6-8ab6-835869313d82");
//		doctorModel.setJobIcon("a375ea8d-14f8-4873-a7c8-74b5287767bc");
//		doctorModel.setDoctorIcon("5b182d51-404d-4acf-b3c0-650173bdfef3");
//		doctorModel.setStatus(56423982);
//		doctorModel.setAge(86505778);
//		doctorModel.setServiceItem(90817209);
//		doctorModel.setIsShow(25104005);
//		doctorModel.setAssistantIcon("e9f35a2a-9aec-4a74-93c5-bb31e5664433");
//		doctorModel.setSource(56788873);
//		doctorModel.setAddDate(new Date());
//		doctorModel.setFailureReason("a4be11d4-e41c-4e43-89e5-d388731daf62");
//		String pkValue = doctorModel.getDoctorId();
//		saveModel(doctorModel);
//
//		//DoctorModel updateModel = createModel();
//		DoctorModel updateModel = new DoctorModel();
//		updateModel.setDoctorId("5afea00b-66e8-4d76-8133-02d1d91e");
//		updateModel.setRealName("d2c9f8b0-99e9-4aad-b60c-f10d52e0a4e7");
//		updateModel.setPhone("5a62e07e-23ef-4c58-b8ce-2176b963");
//		updateModel.setIcon("7c7ef63c-0cf6-431a-b6d7-6e30c1c962c2");
//		updateModel.setGender(27901501);
//		updateModel.setBirthday(new Date());
//		updateModel.setHospitalName("8986791c-c43e-4154-9b40-24d6a81d");
//		updateModel.setDepartment(28717399);
//		updateModel.setTitle("0cc45cf1-51bc-4fed-ac42-624e4aaf");
//		updateModel.setAdept("ba16c240-f763-4f4e-8945-23f46cdf7645");
//		updateModel.setEducation("13683543-0d49-47ec-b173-37b0272c");
//		updateModel.setWorkYear(88704378);
//		updateModel.setIntro("f9878f15-ab96-4c58-b5c1-6e6a895b7f78");
//		updateModel.setIdIcon("8e6c4b52-0bd3-40dd-95a4-5120eaadc9e9");
//		updateModel.setJobIcon("8d1a9c73-0f69-490f-9996-c70a1ebe137a");
//		updateModel.setDoctorIcon("c0a155ad-8665-4eac-8d9e-8a2088b76a6f");
//		updateModel.setStatus(52427325);
//		updateModel.setAge(56404754);
//		updateModel.setServiceItem(51030316);
//		updateModel.setIsShow(74192210);
//		updateModel.setAssistantIcon("2d3dea27-c6bd-470b-9453-f4e563a495d0");
//		updateModel.setSource(82293396);
//		updateModel.setAddDate(new Date());
//		updateModel.setFailureReason("b525c84a-3eab-481d-a165-b1fafd445374");
//
//		updateModel.setDoctorId(pkValue);
//		Integer updateResult = doctorService.updateByPrimaryKey(updateModel);
//		assertEquals(new Integer(1), updateResult);
//		DoctorModel findModel = doctorService.findByPrimaryKey(pkValue);
//		assertEquals(pkValue, findModel.getDoctorId());
//		assertEquals(updateModel.getRealName(), findModel.getRealName());
//
//		cleanModel(pkValue);
//	}
//
//	@Test
//	public void testDeleteByPrimaryKey() throws Exception{
//		DoctorModel doctorModel = new DoctorModel();
//		doctorModel.setDoctorId("23141946-7fdc-4f4d-b474-06faeb55");
//		doctorModel.setRealName("17a0a278-5453-47ad-b272-075484c24e6b");
//		doctorModel.setPhone("a40201c3-38bc-444c-b1e5-896e7e28");
//		doctorModel.setIcon("ebaa74d7-6edd-4ddc-b6a0-e66d7c3394f9");
//		doctorModel.setGender(73435710);
//		doctorModel.setBirthday(new Date());
//		doctorModel.setHospitalName("3b5a9ca0-46ec-4fe6-bc86-718c3009");
//		doctorModel.setDepartment(14978944);
//		doctorModel.setTitle("2a61b821-6029-4c23-9a27-d41f7fd4");
//		doctorModel.setAdept("4742a467-403b-41cf-bb66-0b1eda04e71e");
//		doctorModel.setEducation("55c87584-0dc2-4add-b4dd-c1fe0cbc");
//		doctorModel.setWorkYear(67435201);
//		doctorModel.setIntro("960b12ca-d5d6-4ead-b692-52fb016dde71");
//		doctorModel.setIdIcon("f743bab4-0ba3-4cce-b64a-bacc33b7917d");
//		doctorModel.setJobIcon("31bdd4d9-c05d-4dc0-9370-a795045930c4");
//		doctorModel.setDoctorIcon("7206bd72-b7cf-46c5-8596-bf1e968eaed3");
//		doctorModel.setStatus(90808179);
//		doctorModel.setAge(83519832);
//		doctorModel.setServiceItem(47646429);
//		doctorModel.setIsShow(59450834);
//		doctorModel.setAssistantIcon("a70de3ea-a69d-462b-856c-0e49ffccb052");
//		doctorModel.setSource(96202120);
//		doctorModel.setAddDate(new Date());
//		doctorModel.setFailureReason("c4bdb3a7-e6cd-4e3c-b2eb-126adf0496d6");
//		String pkValue = doctorModel.getDoctorId();
//		saveModel(doctorModel);
//
//		Integer deleteResult = doctorService.deleteByPrimaryKey(pkValue);
//		assertEquals(new Integer(1), deleteResult);
//		DoctorModel findModel = doctorService.findByPrimaryKey(pkValue);
//		assertNull(findModel);
//	}
//
//	private void saveModel(DoctorModel doctorModel) throws Exception {
//		Integer createResult = doctorService.create(doctorModel);
//		assertEquals(createResult, new Integer(1));
//	}
//
//	private void cleanModel(String pk) throws Exception {
//		Integer deleteResult = doctorService.deleteByPrimaryKey(pk);
//		assertEquals(deleteResult, new Integer(1));
//	}
//
//	@SuppressWarnings("unused")
//	private DoctorModel createModel() {
//		DoctorModel doctorModel = new DoctorModel();
//		doctorModel.setDoctorId("589a4c07-0969-4ad5-99cf-5aa39aa0");
//		doctorModel.setRealName("793c6011-ac5f-405f-9ddf-f95958dedda8");
//		doctorModel.setPhone("e45091d4-cacb-46ee-b844-3cf70963");
//		doctorModel.setIcon("4feaa835-f705-48ec-8774-50e30046a5ff");
//		doctorModel.setGender(63143528);
//		doctorModel.setBirthday(new Date());
//		doctorModel.setHospitalName("d721eb80-0321-4587-a9b9-6cf8dffc");
//		doctorModel.setDepartment(18305906);
//		doctorModel.setTitle("d9fe677d-b523-4a6c-bab4-777d3a58");
//		doctorModel.setAdept("78c4430b-6cbc-44f9-abed-2b4c43ce9066");
//		doctorModel.setEducation("ef9ad898-e217-45ff-9f51-ca58684b");
//		doctorModel.setWorkYear(35154740);
//		doctorModel.setIntro("9e55c215-9e8c-41f1-818e-c6207fc140e2");
//		doctorModel.setIdIcon("db6650e1-5ec8-45b9-813c-10d52d514137");
//		doctorModel.setJobIcon("42cf782f-f516-401f-b435-1903845ebcb2");
//		doctorModel.setDoctorIcon("d43e6ef0-74aa-4408-b97f-65c486a350a1");
//		doctorModel.setStatus(35220007);
//		doctorModel.setAge(49183797);
//		doctorModel.setServiceItem(57192059);
//		doctorModel.setIsShow(69039435);
//		doctorModel.setAssistantIcon("3169643b-2655-42ef-966e-7f088e67064a");
//		doctorModel.setSource(40420458);
//		doctorModel.setAddDate(new Date());
//		doctorModel.setFailureReason("2176d58b-1ce1-49e4-b7b5-7f5d187dfe0e");
//		return doctorModel;
//	}
//
//
//}
