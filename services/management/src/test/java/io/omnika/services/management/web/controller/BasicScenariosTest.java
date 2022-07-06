package io.omnika.services.management.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.omnika.common.rest.services.management.dto.Tenant;
import io.omnika.common.rest.services.management.dto.User;
import io.omnika.common.rest.services.management.dto.auth.SetPasswordDto;
import io.omnika.common.rest.services.management.dto.auth.TokenDto;
import io.omnika.common.model.channel.TelegramBotChannelConfig;
import io.omnika.common.rest.services.management.dto.manager.CreateManagerDto;
import io.omnika.common.rest.services.management.dto.manager.ManagerDto;
import io.omnika.services.management.AbstractIntegrationTest;
import io.omnika.services.management.model.UserEntity;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

/** This test created to simplify testing of common functionality of
 * service.
 * Important that tests is ordered according to using
 * temporary database. */
class BasicScenariosTest extends AbstractIntegrationTest {

    // TODO: add logging of data flow. It will help to analyze problems
    // TODO: split this scenario to methods: signUp-login, createTenant, createManager-activateManager
    // TODO: create different tenants and managers to test how to queris
    @Test
    @Order(1)
    void testSignUpLoginCreateManagerActivateManager() {
        String testTenantName = "Test tenant name";

        signUp(testUserEmail, testUserPass);
        TokenDto token = login(testUserEmail, testUserPass);

        assertNotNull(token);

        User currentUser = getCurrentUser(token);

        assertNotNull(token);
        assertEquals(testUserEmail, currentUser.getEmail());

        Tenant tenant = new Tenant();
        tenant.setName(testTenantName);
//        tenantDto.setUser(currentUser);
        Tenant createdTenant = createTenant(tenant, token);

        assertNotNull(createdTenant);
        assertEquals(testTenantName, tenant.getName());

        List<Tenant> tenantsOfCurrentUser = listTenants(token);

        assertEquals(1, tenantsOfCurrentUser.size());
        assertEquals(tenant.getName(), tenantsOfCurrentUser.get(0).getName());

        CreateManagerDto createManagerDto = new CreateManagerDto();
        createManagerDto.setEmail(testManagerEmail);
        createManagerDto.setName("Manager");
//        createManagerDto.setTenantId(createdTenant.getId());

        ManagerDto managerDto = createManager(createManagerDto, token);

        assertNotNull(managerDto);

        UserEntity notActivatedManagerUser = userRepository.findByEmail(testManagerEmail).orElseThrow(NullPointerException::new);

        assertFalse(notActivatedManagerUser.isActive());
        UUID managerActivationToken = notActivatedManagerUser.getActivationToken();

        SetPasswordDto setPasswordToManagerUser = new SetPasswordDto();
        setPasswordToManagerUser.setPassword(testUserPass);

        TokenDto managerActivatedToken = activateUser(managerActivationToken, setPasswordToManagerUser);

        UserEntity mangerUserActivated = userRepository.findByEmail(testManagerEmail).orElseThrow(NullPointerException::new);

        assertTrue(mangerUserActivated.isActive());
        assertNull(mangerUserActivated.getActivationToken());
    }

    // TODO: add more channels to check quering list of channels
    @Test
    @Order(2)
    void testCreateChannelForTenant() {
        String telegramChannelName = "First telegram channel";
        String botName = "BOT_NAME";
        String apiKey = "123123";

        TokenDto token = login(testUserEmail, testUserPass);

        List<Tenant> userTenants = listTenants(token);
        Tenant tenant = userTenants.get(0);

        TelegramBotChannelConfig telegramBotChannelDto = new TelegramBotChannelConfig();
//        telegramBotChannelDto.setTenantDto(tenantDto);
//        telegramBotChannelDto.setName(telegramChannelName);
//        telegramBotChannelDto.setChannelType(ChannelType.TELEGRAM_BOT);
        telegramBotChannelDto.setBotName(botName);
        telegramBotChannelDto.setApiKey(apiKey);

        TelegramBotChannelConfig telegramBotChannel = createTelegramBotChannel(telegramBotChannelDto, token);

        assertNotNull(telegramBotChannel);
        // TODO: check list of tenant's channels
    }
}
