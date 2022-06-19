package io.omnika.services.management;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.omnika.common.rest.services.management.dto.TenantDto;
import io.omnika.common.rest.services.management.dto.UserDto;
import io.omnika.common.rest.services.management.dto.auth.SetPasswordDto;
import io.omnika.common.rest.services.management.dto.auth.SigningDto;
import io.omnika.common.rest.services.management.dto.auth.TokenDto;
import io.omnika.common.rest.services.management.dto.channel.TelegramBotChannelConfig;
import io.omnika.common.rest.services.management.dto.manager.CreateManagerDto;
import io.omnika.common.rest.services.management.dto.manager.ManagerDto;
import io.omnika.services.management.repository.UserRepository;
import io.omnika.services.management.web.controller.AuthControllerImpl;
import io.omnika.services.management.web.controller.ChannelControllerImpl;
import io.omnika.services.management.web.controller.TenantControllerImpl;
import java.util.List;
import java.util.UUID;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagementApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        properties = {
                "spring.jpa.hibernate.ddl-auto=create",
                "spring.flyway.enabled=false",
                "spring.datasource.url = jdbc:h2:mem:test;DATABASE_TO_UPPER=false;INIT=CREATE SCHEMA IF NOT EXISTS public;",
                "spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.H2Dialect"
        }
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {
        AuthControllerImpl.class,
        TenantControllerImpl.class,
//        ManagerControllerImpl.class,
        ChannelControllerImpl.class,
        UserRepository.class
})
public abstract class AbstractIntegrationTest {

    protected static final String testUserEmail = "test@gmail.com";
    protected static final String testUserPass = "1234Test";

    protected static final String testManagerEmail = "test_manager@gmail.com";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected UserRepository userRepository;

    @SneakyThrows(Exception.class)
    protected void signUp(String email, String password) {
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(
                post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                SigningDto.builder().email(email).password(password).build())))
                .andExpect(status().isOk());
    }

    @SneakyThrows(Exception.class)
    protected TokenDto login(String email, String password) {
        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult mvcResult = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(SigningDto.builder().email(email).password(password).build())))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TokenDto.class);
    }

    @SneakyThrows
    protected TokenDto activateUser(UUID activationToken, SetPasswordDto setPasswordDto) {
        MvcResult mvcResult = mockMvc.perform(
                post("/auth/{activationToken}", activationToken.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(setPasswordDto)))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TokenDto.class);
    }

    @SneakyThrows
    protected UserDto getCurrentUser(TokenDto token) {
        MvcResult mvcResult = mockMvc.perform(get("/user/current").header("X-Authorization", token.getAuthToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDto.class);
    }

    @SneakyThrows
    protected TenantDto createTenant(TenantDto tenantDto, TokenDto token) {
        MvcResult mvcResult = mockMvc.perform(post("/tenant").header("X-Authorization", token.getAuthToken())
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(tenantDto)))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TenantDto.class);
    }

    @SneakyThrows
    protected List<TenantDto> listTenants(TokenDto tokenDto) {
        MvcResult mvcResult = mockMvc.perform(get("/tenant").header("X-Authorization", tokenDto.getAuthToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
    }

    @SneakyThrows
    protected ManagerDto createManager(CreateManagerDto createManagerDto, TokenDto tokenDto) {
        MvcResult mvcResult = mockMvc.perform(post("/manager").header("X-Authorization", tokenDto.getAuthToken())
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createManagerDto)))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ManagerDto.class);
    }

    @SneakyThrows
    protected TelegramBotChannelConfig createTelegramBotChannel(TelegramBotChannelConfig telegramBotChannelDto, TokenDto tokenDto) {
        MvcResult mvcResult = mockMvc.perform(post("/channel/telegram")
                .header("X-Authorization", tokenDto.getAuthToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(telegramBotChannelDto)))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TelegramBotChannelConfig.class);
    }
}
