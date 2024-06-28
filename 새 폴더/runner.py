import pygame
import random

# Initialize Pygame
pygame.init()

# Screen dimensions
screen_width = 800
screen_height = 600
screen = pygame.display.set_mode((screen_width, screen_height))
pygame.display.set_caption("Infinite Runner Game")

# Colors
black = (0, 0, 0)
white = (255, 255, 255)
red = (255, 0, 0)
green = (0, 255, 0)

# Game constants
player_size = 50
player_x = screen_width // 2
player_y = screen_height - player_size - 10
player_speed = 5

obstacle_width = 50
obstacle_height = 50
obstacle_speed = 7
obstacle_frequency = 25  # Higher value means less frequent

item_width = 30
item_height = 30
item_speed = 5
item_frequency = 200  # Higher value means less frequent

# Scoring
score = 0
font = pygame.font.SysFont('Arial', 24)

# Game over flag
game_over = False

# Obstacles and items
obstacles = []
items = []

# Main game loop
running = True
while running:
    screen.fill(black)

    # Event handling
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

    keys = pygame.key.get_pressed()
    if keys[pygame.K_LEFT] and player_x > 0:
        player_x -= player_speed
    if keys[pygame.K_RIGHT] and player_x < screen_width - player_size:
        player_x += player_speed
    if keys[pygame.K_UP] and player_y > 0:
        player_y -= player_speed
    if keys[pygame.K_DOWN] and player_y < screen_height - player_size:
        player_y += player_speed

    # Generate obstacles
    if random.randint(1, obstacle_frequency) == 1:
        obstacle_x = random.randint(0, screen_width - obstacle_width)
        obstacles.append(pygame.Rect(obstacle_x, 0, obstacle_width, obstacle_height))

    # Generate items
    if random.randint(1, item_frequency) == 1:
        item_x = random.randint(0, screen_width - item_width)
        items.append(pygame.Rect(item_x, 0, item_width, item_height))

    # Move obstacles
    for obstacle in obstacles:
        obstacle.y += obstacle_speed
        if obstacle.y > screen_height:
            obstacles.remove(obstacle)
        if obstacle.colliderect(pygame.Rect(player_x, player_y, player_size, player_size)):
            game_over = True

    # Move items
    for item in items:
        item.y += item_speed
        if item.y > screen_height:
            items.remove(item)
        if item.colliderect(pygame.Rect(player_x, player_y, player_size, player_size)):
            score += 1
            items.remove(item)

    # Draw player
    pygame.draw.rect(screen, white, (player_x, player_y, player_size, player_size))

    # Draw obstacles
    for obstacle in obstacles:
        pygame.draw.rect(screen, red, obstacle)

    # Draw items
    for item in items:
        pygame.draw.rect(screen, green, item)

    # Draw score
    score_text = font.render(f"Score: {score}", True, white)
    screen.blit(score_text, (10, 10))

    # Game over
    if game_over:
        game_over_text = font.render("Game Over! Press R to Restart", True, white)
        screen.blit(game_over_text, (screen_width // 2 - 150, screen_height // 2 - 20))
        if keys[pygame.K_r]:
            game_over = False
            player_x, player_y = screen_width // 2, screen_height - player_size - 10
            obstacles.clear()
            items.clear()
            score = 0

    pygame.display.flip()
    pygame.time.Clock().tick(60)

pygame.quit()
