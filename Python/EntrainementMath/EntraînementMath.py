import random
import sys
import time
import os.path
from tkinter import *
from tkinter.ttk import *
from pickle import *

SAVE_FILE_NAME = 'progression_save.csv'

class Math:
    def __init__(self, name):
        self.name = name
        self.strikes = 0
        self.difficulty = [100, 100, 10, 10]
        self.progression = 0
        self.get_time = []
        self.suite = 0
        self.start_time = 0
        self.total = 0
        if os.path.isfile(os.path.join(sys.path[0], SAVE_FILE_NAME)):
            file = open(os.path.join(sys.path[0], SAVE_FILE_NAME), "rb")
            dictionary = load(file)
            if self.name + "strikes" in dictionary:
                self.strikes = dictionary[self.name + "strikes"]

            if self.name + "get_time" in dictionary:
                self.get_time = dictionary[self.name + "get_time"]

            if self.name + "difficulty" in dictionary:
                self.difficulty = dictionary[self.name + "difficulty"]

            if self.name + "progression" in dictionary:
                self.progression = dictionary[self.name + "progression"]
        else:
            file = open(os.path.join(sys.path[0], SAVE_FILE_NAME), "wb")
            dictionary = {self.name + "progression": self.progression,
                          self.name + "difficulty": self.difficulty, self.name + "get_time": self.get_time, self.name + "strikes": self.strikes}
            dump(dictionary, file)
        file.close()

    def termine(self):
        moyenne = 0
        for i in range(len(self.get_time)):
            moyenne += self.get_time[i]
        moyenne = moyenne / len(self.get_time)
        text.set("Entraînement terminé")
        text_desc1.set("vous avez un temps de réponse moyen de " +
                       str(int(moyenne)) + " seconds")
        text_desc2.set("Voici vos statistiques :\n" + "Difficulté : " + str(self.difficulty) +
                       "\n" + "Progression : " + str(self.progression) + "\n" + "Strikes : " + str(self.strikes))
        text_desc4.set("")

        if os.path.isfile('math_save.csv'):
            file = open("math_save.csv", "rb")
            dictionary = load(file)
            file.close()
        self.get_time = []
        dictionary.update({self.name + "progression": self.progression,
                           self.name + "difficulty": self.difficulty, self.name + "get_time": self.get_time, self.name + "strikes": self.strikes})
        file = open("math_save.csv", "wb")
        dump(dictionary, file)
        file.close()
        if (int)(saisie.get()) == 1:
            self.suite = 1
            text_desc4.set("")
            text_desc3.set("")
            text_desc2.set("")
            self.enter_name()
        else:
            self.suite = 4
            print("Arreter")

    def calcul(self):
        text_desc1.set("")
        try:
            respond = int(saisie.get())
            if respond == self.total:
                text_desc1.set("Good !")
                self.get_time.append((time.time() - self.start_time))
            else:
                text_desc1.set("Wrong !\nC'était " + str(self.total))
                self.get_time.append(20)

            if len(self.get_time) == 10:
                self.suite = 3
                moyenne = 0
                for i in range(len(self.get_time)):
                    moyenne += self.get_time[i]
                moyenne = int(moyenne / len(self.get_time))

                if moyenne < 10:
                    text_desc2.set("\nDifficulté à peine augmenté !")
                    self.strikes += 1
                    for i in range(self.progression + 1):
                        self.difficulty[i] += int(self.difficulty[i] / 5)

                    if self.progression < 3:
                        text.set("Bravo !")
                        if self.strikes == 4:
                            self.progression += 1
                            modes = ["+", "-", "*", "%"]
                            text_desc3.set(
                                "4 Strikes atteints, le prochain entraînement sera avec les " + str(modes[self.progression]))
                            self.strikes = 0
                        else:
                            text_desc3.set("Strikes atteint, niveau supérieur")

                elif moyenne >= 10:
                    self.strikes = 0
                    text_desc3.set(
                        "Mauvais score, la difficulté est légèrement réduite et les strikes sont annulés")
                    for i in range(self.progression + 1):
                        self.difficulty[i] -= int(self.difficulty[i] / 5)
                text_desc4.set(
                    "Continuer à jouer = 1\nArrêter le programme = 2")

            else:
                self.new_calcul()

        except:
            text_desc1.set("Erreur, veuillez saisir un nombre")

    def enter_name(self):
        text.set("Jouer = 0\nChanger de profil = 1\nArrêter le programme = 2")
        text_desc1.set("Bonjour " +
                       self.name + " voici vos statistiques :\n" + "Difficulté : " + str(self.difficulty) +
                       "\n" + "Progression : " + str(self.progression) + "\n" + "Strikes : " + str(self.strikes))
        self.suite = 1

    def ask(self):
        response = int(saisie.get())
        if response != 0 and response != 1 and response != 2:
            text_desc1.set("Erreur de saisie")
        elif response == 0 and len(self.get_time) < 10:
            saisie.delete(0, END)
            self.suite = 2
            self.new_calcul()
        elif response == 1:
            saisie.delete(0, END)
            self.suite = 0
            text.set("Rentrez le nom de votre partie")
        else:
            tk.destroy()

    def new_calcul(self):
        self.start_time = time.time()
        modes = ["+", "-", "*", "%"]
        current_progression = random.randint(0, int(self.progression))
        first = random.randint(
            1, int(self.difficulty[current_progression]))
        segund = random.randint(
            1, int(self.difficulty[current_progression]))
        ask = str(first) + modes[current_progression] + str(segund)
        self.total = {
            "+": lambda x, y: x + y,
            "-": lambda x, y: x - y,
            "*": lambda x, y: x * y,
            "/": lambda x, y: x % y
        }[modes[current_progression]](first, segund)
        text.set("Quel est le résultat de : ")
        text_desc2.set(ask)


def retrieve(event):
    global Game_name
    if Game_name.suite == 0:
        Game_name = saisie.get()
        Game_name = Math(Game_name)
        Game_name.enter_name()
        saisie.delete(0, END)
    elif Game_name.suite == 1:
        text_desc1.set("")
        Game_name.ask()
        saisie.delete(0, END)
    elif Game_name.suite == 2 and len(Game_name.get_time) < 10:
        Game_name.calcul()
        saisie.delete(0, END)
    elif Game_name.suite == 3:
        Game_name.termine()
        saisie.delete(0, END)
    else:
        tk.destroy()


suite = 0

tk = Tk()
x, y = 300, 400

text, text_desc1, text_desc2, text_desc3, text_desc4 = StringVar(
), StringVar(), StringVar(), StringVar(), StringVar()
saisie = Entry(tk)

entrer = Button(tk, text="Entrer", command=lambda: retrieve(x))
tk.bind('<Return>', retrieve)

titre = Label(tk, font="Times 20 bold", textvariable=text)
Description1 = Label(tk, textvariable=text_desc1)
Description2 = Label(tk, textvariable=text_desc2)
Description3 = Label(tk, textvariable=text_desc3)
Description4 = Label(tk, textvariable=text_desc4)

titre.grid(row=0, column=0, ipady=y/10, padx=x/2)
Description1.grid(row=1, column=0, pady=2)
Description2.grid(row=2, column=0, pady=2)
Description3.grid(row=3, column=0, pady=2)
Description4.grid(row=4, column=0, pady=y/20)
saisie.grid(row=5, column=0, pady=y/20)
entrer.grid(row=6, column=0, pady=y/20)


Game_name = ""
Game_name = Math(Game_name)
text.set("Rentrez le nom de votre partie")
saisie.focus()

tk.title("Entrainement Math")
tk.resizable(False, False)
tk.mainloop()
