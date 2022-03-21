import pygame
import time
import random

# Définitions :
BLACK = (0, 0, 0)
RED = (255, 50, 50)
BLUE = (50, 50, 255)
GREY = (150, 150, 150)
WHITE = (255, 255, 255)
width, height = 960, 720
width_plat, height_plat = 20, 200
fps = 60
radius_ball = 30
clock = pygame.time.Clock()


win = pygame.display.set_mode((width, height))
pygame.display.set_caption("Pong v2")

# Load images :
BG_pong = pygame.image.load(
    r'bg_pong1.png')
pygame.font.init()
myfont = pygame.font.SysFont('Comic Sans MS', 50)


class Game:
    def __init__(self):
        self.score_P2, self.score_P1 = 0, 0

    def objets(self):
        textsurface = myfont.render(
            str(self.score_P1) + "   " + str(self.score_P2), False, (255, 255, 255))
        win.blit(BG_pong, (0, 0))
        win.blit(textsurface, (width/2 - 50, height/2 - 50))
        pygame.draw.rect(win, BLUE, self.rect_p1, border_radius=20)
        pygame.draw.rect(win, RED, self.rect_p2, border_radius=20)
        pygame.draw.rect(
            win, GREY, self.ball, border_radius=100)
        pygame.display.update()

    def definitions(self):
        self.ball = pygame.Rect(
            (width - radius_ball)/2, (height - radius_ball)/2, radius_ball, radius_ball)
        self.speed = 7
        self.rect_p1 = pygame.Rect(
            0, (height - height_plat)/2, width_plat, height_plat)

        self.rect_p2 = pygame.Rect(
            width - width_plat, (height - height_plat)/2, width_plat, height_plat)

    def run(self):
        pygame.font.init()
        self.definitions()
        self.objets()
        run = True
        s = False
        x, y = 4, 4
        while run:
            clock.tick(fps)
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    run = False
            keys = pygame.key.get_pressed()
            if (self.rect_p2.top < height - height_plat - 5 and keys[pygame.K_DOWN]):
                self.rect_p2.move_ip(0, self.speed)
            if (self.rect_p2.top > 5 and keys[pygame.K_UP]):
                self.rect_p2.move_ip(0, -self.speed)
            if (self.rect_p1.top < height - height_plat - 5 and keys[pygame.K_s]):
                self.rect_p1.move_ip(0, self.speed)
            if (self.rect_p1.top > 5 and keys[pygame.K_z]):
                self.rect_p1.move_ip(0, -self.speed)
            if (((height - height_plat)/2) != self.rect_p1.top or ((height - height_plat)/2) != self.rect_p2.top and s == False):
                s = True
            if s:
                if self.ball.colliderect(self.rect_p2):
                    x = -x
                    if (y < 0):
                        y = -random.uniform(1, 8)
                    else:
                        y = random.uniform(1, 8)
                    self.ball.move_ip(x, y)

                elif self.ball.colliderect(self.rect_p1):
                    x = -x
                    x += 2
                    if (y < 0):
                        y = -random.uniform(1, 8)
                    else:
                        y = random.uniform(1, 8)
                    self.ball.move_ip(x, y)

                elif self.ball.top <= 0 or self.ball.bottom >= height:
                    y = -y
                    self.ball.move_ip(x, y)

                elif self.ball.left < 0 or self.ball.right > width:
                    x, y, s = 4, 4, False
                    if self.ball.left < 0:
                        self.score_P2 += 1
                    elif self.ball.right > width:
                        self.score_P1 += 1
                    self.definitions()
                    time.sleep(0.5)

                else:
                    self.ball.move_ip(x, y)

            self.objets()
        pygame.quit()


game = Game()
game.run()
