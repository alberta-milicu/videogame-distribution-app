package com.example.videogame_distribution_app.controller;

import com.example.videogame_distribution_app.entity.Game;
import com.example.videogame_distribution_app.entity.User;
import com.example.videogame_distribution_app.entity.Customer;
import com.example.videogame_distribution_app.service.GameService;
import com.example.videogame_distribution_app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GameService gameService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GameController gameController;

    private Game game;
    private User user;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();

        user = new Customer();
        user.setId(1);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        ((Customer) user).setMembershipType("Gold");
        ((Customer) user).setPoints(150);

        game = new Game();
        game.setId(1L);
        game.setName("Test Game");
        game.setUser(user);
    }


    @Test
    public void testGetAllGames() throws Exception {
        when(gameService.getAllGames()).thenReturn(Collections.singletonList(game));

        mockMvc.perform(get("/api/games"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Game"));

        verify(gameService, times(1)).getAllGames();
    }

    @Test
    public void testGetGameById() throws Exception {
        when(gameService.getGameById(1L)).thenReturn(Optional.of(game));

        mockMvc.perform(get("/api/games/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Game"));

        verify(gameService, times(1)).getGameById(1L);
    }


    @Test
    public void testCreateGame_InvalidUser() throws Exception {
        mockMvc.perform(post("/api/games")
                        .contentType("application/json")
                        .content("{\"name\":\"New Game\", \"user\":{\"id\":0}}"))
                .andExpect(status().isBadRequest());

        verify(gameService, times(0)).createGame(any(Game.class));
    }
}
