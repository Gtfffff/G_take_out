package com.g.customer;


/**
 * @Author: Gtf
 * @Date: 2022/5/14-05-14-0:29
 * @Description: 不知道为什么启用security环境的配置失效
 * @Version: 1.0
 */
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AddressApiTest {
//
////    private final AddressApi addressApi;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void setup(){
//         mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .apply(springSecurity())
//                .build();
//    }
//
//    @Test
////    @WithMockUser
//    public void securityTest() throws Exception {
//
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/test");
//        ResultActions perform = mockMvc.perform(requestBuilder);
//        StatusResultMatchers status = MockMvcResultMatchers.status();
//        ResultMatcher ok = status.isOk();
//        perform.andExpect(ok);
//    }
//}
