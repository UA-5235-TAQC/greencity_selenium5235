package org.greencity.api.EcoNewsById;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.greencity.api.models.econews.EcoNewsResponse;
import org.greencity.api.models.econews.UpdateEcoNewsDto;
import org.greencity.api.testrunners.CreateNewsRunner;
import org.greencity.utils.api.EcoNewsAssertions;
import org.greencity.utils.api.EcoNewsDtoFactory;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.greencity.utils.api.ApiTestAssertions.assertOk;

@Epic("EcoNews API")
@Feature("Update EcoNews without image")
@Severity(SeverityLevel.NORMAL)
@Tag("EcoNewsById API")
public class UpdateEcoNewsByIdWithoutImageTest extends CreateNewsRunner {

    @Test
    @Story("Update EcoNews without image")
    @Description("Verify that updating EcoNews without providing an image works correctly")
    public void testUpdateEcoNewsByIdWithoutImage() {
        EcoNewsDtoFactory dtoFactory = new EcoNewsDtoFactory(ecoNewsId);
        UpdateEcoNewsDto updateDto = dtoFactory.updateDtoUa();

        Response response = ecoNewsClient.updateEcoNewsById(ecoNewsId, updateDto);
        assertOk(response);

        EcoNewsResponse ecoNews = response.as(EcoNewsResponse.class);
        LocalDate expectedDate = LocalDate.now(ZoneOffset.UTC);
        EcoNewsAssertions.assertEcoNewsResponse(
                ecoNews,
                updateDto.getId(),
                updateDto.getTitle(),
                updateDto.getContent(),
                updateDto.getShortInfo(),
                expectedDate,
                updateDto.getTagsEn(),
                updateDto.getTagsUk(),
                null,
                null,
                false,
                false
        );
    }
}
