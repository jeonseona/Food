import pygame
import random

# Initialize Pygame
pygame.init()

# Screen dimensions
screen_width = 300
screen_height = 600
screen = pygame.display.set_mode((screen_width, screen_height))
pygame.display.set_caption("Tetris")

# Colors
colors = [
    (0, 0, 0),
    (255, 0, 0),
    (0, 255, 0),
    (0, 0, 255),
    (255, 255, 0),
    (255, 165, 0),
    (255, 0, 255),
    (0, 255, 255)
]

# Tetris shapes
shapes = [
    [[1, 1, 1, 1]],                # Line
    [[1, 1], [1, 1]],              # Square
    [[0, 1, 0], [1, 1, 1]],        # T-shape
    [[1, 0, 0], [1, 1, 1]],        # L-shape
    [[0, 0, 1], [1, 1, 1]],        # Reverse L-shape
    [[1, 1, 0], [0, 1, 1]],        # S-shape
    [[0, 1, 1], [1, 1, 0]]         # Z-shape
]

# Grid size
grid_width = 10
grid_height = 20
cell_size = 30

# Fonts
font = pygame.font.SysFont('Arial', 24)

# Game variables
score = 0
game_over = False
grid = [[0] * grid_width for _ in range(grid_height)]

class Tetromino:
    def __init__(self, shape):
        self.shape = shape
        self.color = random.randint(1, len(colors) - 1)
        self.rotation = 0
        self.x = grid_width // 2 - len(shape[0]) // 2
        self.y = 0

    def image(self):
        return self.shape

    def rotate(self):
        self.rotation = (self.rotation + 1) % len(self.shape)
        self.shape = [list(row) for row in zip(*self.shape[::-1])] # Rotate the shape

def check_collision(grid, shape, offset):
    off_x, off_y = offset
    for y, row in enumerate(shape):
        for x, cell in enumerate(row):
            if cell and (x + off_x < 0 or x + off_x >= grid_width or y + off_y >= grid_height or grid[y + off_y][x + off_x]):
                return True
    return False

def merge_shape(grid, shape, offset):
    off_x, off_y = offset
    for y, row in enumerate(shape):
        for x, cell in enumerate(row):
            if cell:
                grid[y + off_y][x + off_x] = cell

def clear_lines(grid, score):
    full_rows = [i for i, row in enumerate(grid) if all(row)]
    if full_rows:
        for i in full_rows:
            del grid[i]
            grid.insert(0, [0 for _ in range(grid_width)])
        score += 10 * len(full_rows)
    return score

def draw_grid(screen, grid):
    for y, row in enumerate(grid):
        for x, cell in enumerate(row):
            pygame.draw.rect(screen, colors[cell], (x * cell_size, y * cell_size, cell_size, cell_size), 0)
    for x in range(grid_width):
        pygame.draw.line(screen, (128, 128, 128), (x * cell_size, 0), (x * cell_size, screen_height))
    for y in range(grid_height):
        pygame.draw.line(screen, (128, 128, 128), (0, y * cell_size), (screen_width, y * cell_size))

def draw_tetromino(screen, tetromino):
    shape = tetromino.image()
    for y, row in enumerate(shape):
        for x, cell in enumerate(row):
            if cell:
                pygame.draw.rect(screen, colors[tetromino.color], ((tetromino.x + x) * cell_size, (tetromino.y + y) * cell_size, cell_size, cell_size), 0)

def run_game():
    global grid, score, game_over

    clock = pygame.time.Clock()
    current_tetromino = Tetromino(random.choice(shapes))
    next_tetromino = Tetromino(random.choice(shapes))
    fall_time = 0

    while not game_over:
        screen.fill((0, 0, 0))
        draw_grid(screen, grid)
        draw_tetromino(screen, current_tetromino)

        # Draw score
        score_text = font.render(f"Score: {score}", True, (255, 255, 255))
        screen.blit(score_text, (10, 10))

        pygame.display.flip()

        fall_speed = 0.3
        fall_time += clock.get_rawtime()
        clock.tick()

        if fall_time / 1000 >= fall_speed:
            fall_time = 0
            current_tetromino.y += 1
            if check_collision(grid, current_tetromino.image(), (current_tetromino.x, current_tetromino.y)):
                current_tetromino.y -= 1
                merge_shape(grid, current_tetromino.image(), (current_tetromino.x, current_tetromino.y))
                current_tetromino = next_tetromino
                next_tetromino = Tetromino(random.choice(shapes))
                score = clear_lines(grid, score)
                if check_collision(grid, current_tetromino.image(), (current_tetromino.x, current_tetromino.y)):
                    game_over = True

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                return
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_LEFT:
                    current_tetromino.x -= 1
                    if check_collision(grid, current_tetromino.image(), (current_tetromino.x, current_tetromino.y)):
                        current_tetromino.x += 1
                if event.key == pygame.K_RIGHT:
                    current_tetromino.x += 1
                    if check_collision(grid, current_tetromino.image(), (current_tetromino.x, current_tetromino.y)):
                        current_tetromino.x -= 1
                if event.key == pygame.K_DOWN:
                    current_tetromino.y += 1
                    if check_collision(grid, current_tetromino.image(), (current_tetromino.x, current_tetromino.y)):
                        current_tetromino.y -= 1
                if event.key == pygame.K_UP:
                    current_tetromino.rotate()
                    if check_collision(grid, current_tetromino.image(), (current_tetromino.x, current_tetromino.y)):
                        current_tetromino.rotate()
                        current_tetromino.rotate()
                        current_tetromino.rotate()
                if event.key == pygame.K_r and game_over:
                    grid = [[0] * grid_width for _ in range(grid_height)]
                    score = 0
                    game_over = False
                    current_tetromino = Tetromino(random.choice(shapes))
                    next_tetromino = Tetromino(random.choice(shapes))

        if game_over:
            game_over_text = font.render("Game Over! Press R to Restart", True, (255, 255, 255))
            screen.blit(game_over_text, (screen_width // 2 - 150, screen_height // 2 - 20))
            pygame.display.flip()

run_game()
pygame.quit()
