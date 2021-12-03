package com.advance.mistra;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import javax.annotation.Resource;

import com.advance.mistra.plugin.esannotationversion.document.MemberDocument;
import com.advance.mistra.plugin.esannotationversion.document.dto.CommunityUserStatusDTO;
import com.advance.mistra.plugin.esannotationversion.document.dto.CyclePayDTO;
import com.advance.mistra.plugin.esannotationversion.document.dto.FreezeInfoDTO;
import com.advance.mistra.plugin.esannotationversion.document.dto.ManualTagsDTO;
import com.advance.mistra.plugin.esannotationversion.document.dto.MemberActivityStatusDTO;
import com.advance.mistra.plugin.esannotationversion.document.dto.SystemTagsDTO;
import com.advance.mistra.plugin.esannotationversion.document.dto.UserBasicDTO;
import com.advance.mistra.plugin.esannotationversion.repo.MemberIndexRepository;
import com.advance.mistra.plugin.esannotationversion.service.InitIndexService;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 描述
 *
 * @author mistra@future.com
 * @date 2021/11/26
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootAdvanceApplication.class)
public class SpringbootAdvanceApplicationTest {

    @Resource
    private InitIndexService initIndexService;

    @Resource
    private MemberIndexRepository memberIndexRepository;

    @Test
    public void testInit() {
        // 初始化索引mapping
        initIndexService.initIndex();
    }

    @Test
    public void creatIndex() {
        // 创建索引文档
        MemberDocument document = new MemberDocument();
        document.setId(20211126L);
        document.setUserId(20211126L);
        document.setCreator("Mistra");
        document.setModifier("Mistra");
        document.setCreateTime(new Date());
        document.setModifyTime(new Date());

        UserBasicDTO.UserBasicDTOBuilder userBasicDTOBuilder = UserBasicDTO.builder();
        userBasicDTOBuilder.city(10001L).accountId(20211126L).gender("MAEL").nickName("昵称").wechatNickname("微信昵称").maskedPhoneNumber("1571947****")
                .consultantEmail("mistra@fiture.com").memberLevel("Lv1").memberUnit("MONTH").memberQuantity(1).memberDayLast(10L)
                .memberSource(Collections.singletonList("TIME")).firstMemberDay(new Date()).memberExpireDay(DateUtils.addDays(new Date(), 10))
                .registerTime(new Date()).wechatAdded(true).wechatAccount("DKJHKAJSHKA").memberType("type1").avatarUrl("http://sdkdjslk.jpg")
                .consultantName("王瑞").measured(true).main(true).homeExperience(true).kol(true).kolType(Collections.singletonList("KOL")).remark("备注");
        document.setBasic(userBasicDTOBuilder.build());

        SystemTagsDTO.SystemTagsDTOBuilder systemTagsDTOBuilder = SystemTagsDTO.builder();
        systemTagsDTOBuilder.todayCompleteCourseNumber(1L).todayCompleteCourseRadio(new BigDecimal("0.5")).todayEffectiveCourseDuration(10000L)
                .todayStartTrainingTime(new Date()).lastSevenCompleteCourseNumber(4L).lastSevenCompleteCourseRadio(new BigDecimal("0.5"))
                .lastSevenEffectiveCourseDuration(20000L).lastThirtyCompleteCourseNumber(2L).lastThirtyCompleteCourseRadio(new BigDecimal("0.5"))
                .lastThirtyEffectiveCourseDuration(400000L).lastCompleteCourseTime(new Date()).memberActive("ACTIVE").vitalityLevel("LV0001")
                .activitySource("TIME").commonMember(true).employee(false).kol(false).vvip(false).wechatUnionId("AKJDHKJDS").delete(false).real(true)
                .workoutAttitude("DASDAD").senseAttitude(Collections.singletonList("ATTITUDE")).workSchedule("DDDD").freezeInfo(FreezeInfoDTO.builder()
                .freezeDate(new Date()).unFreezeDate(new Date()).freezeReason("reason").freezeStatus(true).build())
                .cyclePay(CyclePayDTO.builder().status("status").build()).community(Collections.singletonList(CommunityUserStatusDTO.builder()
                .communityName("社群名称").status("JOIN").communityId(1L).activityCode(Collections.singletonList("TIME")).build()))
                .activity(Collections.singletonList(MemberActivityStatusDTO.builder().activityCode("CODE").status("JOIN").signUpDate(new Date()).build()));
        document.setTags(systemTagsDTOBuilder.build());

        ManualTagsDTO.ManualTagsDTOBuilder manualTagsDTOBuilder = ManualTagsDTO.builder();
        manualTagsDTOBuilder.city(10001L);
        document.setManualTags(manualTagsDTOBuilder.build());
        memberIndexRepository.index(document);
    }
}
