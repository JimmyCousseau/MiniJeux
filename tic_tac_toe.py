# useful libraries import
from tkinter import *

# page creation
root = Tk()
root.title('Tic-Tac-Toe')
root.resizable(0, 0)

# functions
global lst
lst = ['X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X']
global play
play = True


def b_click(b):  # change the content of the case
    if play:
        content = b.cget("text")
        if content == " ":
            b["text"] = lst[0]
            lst.remove(lst[0])


def g_win():  # check if there is a victory
    cases = [b1, b2, b3, b4, b5, b6, b7, b8, b9]
    content = list(map(lambda x: x.cget("text"), cases))
    winposs = [(0, 1, 2), (3, 4, 5), (6, 7, 8), (0, 3, 6), (1, 4, 7), (2, 5, 8), (0, 4, 8), (2, 4, 6)]
    for i in winposs:
        a, b, c = content[i[0]], content[i[1]], content[i[2]]
        d, e, f = cases[i[0]], cases[i[1]], cases[i[2]]
        bg, fg = '#111', '#fff'
        if a == b == c != " ":
            global play
            play = False
            d["bg"], e["bg"], f["bg"] = bg, bg, bg
            d["fg"], e["fg"], f["fg"] = fg, fg, fg
            mes["text"] = "Victory of '" + a + "' player !"
            message.grid(column=0, row=0)


def g_full():  # check if the grid is full
    cases = [b1, b2, b3, b4, b5, b6, b7, b8, b9]
    content = list(map(lambda x: x.cget("text"), cases))
    result = True
    for i in content:
        if i == " ":
            result = False
    if result:
        global play
        play = False
        nomessage.grid(column=0, row=0)


def main_click(b):  # does all the functions
    b_click(b)
    g_win()
    g_full()


def clear():  # clear the grid
    cases = [b1, b2, b3, b4, b5, b6, b7, b8, b9]
    global play
    play = True
    global lst
    lst = ['X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X']
    message.grid_forget()
    nomessage.grid_forget()
    for i in cases:
        i["text"] = ' '
        i["bg"] = '#fff'
        i["fg"] = '#111'


def quitfunction():  # close the game
    root.destroy()


# buttons grid
buttonsframe = Frame(root)
buttonsframe.grid(column=0, row=0)
b1 = Button(buttonsframe, text=" ", font=("Helvetica", 20), height=3, width=7, bg="#fff", fg="#111",
            activebackground="#f9f9f9", bd=0, cursor="hand2", command=lambda: main_click(b1))
b1.grid(column=0, row=0, pady=5, padx=5)
b2 = Button(buttonsframe, text=" ", font=("Helvetica", 20), height=3, width=7, bg="#fff", fg="#111",
            activebackground="#f9f9f9", bd=0, cursor="hand2", command=lambda: main_click(b2))
b2.grid(column=1, row=0, pady=5, padx=5)
b3 = Button(buttonsframe, text=" ", font=("Helvetica", 20), height=3, width=7, bg="#fff", fg="#111",
            activebackground="#f9f9f9", bd=0, cursor="hand2", command=lambda: main_click(b3))
b3.grid(column=2, row=0, pady=5, padx=5)
b4 = Button(buttonsframe, text=" ", font=("Helvetica", 20), height=3, width=7, bg="#fff", fg="#111",
            activebackground="#f9f9f9", bd=0, cursor="hand2", command=lambda: main_click(b4))
b4.grid(column=0, row=1, pady=5, padx=5)
b5 = Button(buttonsframe, text=" ", font=("Helvetica", 20), height=3, width=7, bg="#fff", fg="#111",
            activebackground="#f9f9f9", bd=0, cursor="hand2", command=lambda: main_click(b5))
b5.grid(column=1, row=1, pady=5, padx=5)
b6 = Button(buttonsframe, text=" ", font=("Helvetica", 20), height=3, width=7, bg="#fff", fg="#111",
            activebackground="#f9f9f9", bd=0, cursor="hand2", command=lambda: main_click(b6))
b6.grid(column=2, row=1, pady=5, padx=5)
b7 = Button(buttonsframe, text=" ", font=("Helvetica", 20), height=3, width=7, bg="#fff", fg="#111",
            activebackground="#f9f9f9", bd=0, cursor="hand2", command=lambda: main_click(b7))
b7.grid(column=0, row=2, pady=5, padx=5)
b8 = Button(buttonsframe, text=" ", font=("Helvetica", 20), height=3, width=7, bg="#fff", fg="#111",
            activebackground="#f9f9f9", bd=0, cursor="hand2", command=lambda: main_click(b8))
b8.grid(column=1, row=2, pady=5, padx=5)
b9 = Button(buttonsframe, text=" ", font=("Helvetica", 20), height=3, width=7, bg="#fff", fg="#111",
            activebackground="#f9f9f9", bd=0, cursor="hand2", command=lambda: main_click(b9))
b9.grid(column=2, row=2, pady=5, padx=5)

# no victory message
nomessage = Frame(root)
nomes = Label(nomessage, text="The grid is full !", font=("Helvetica", 10), fg="#111")
nomes.grid(column=0, row=0, columnspan=2, ipady=10, ipadx=20)
btnreplay = Button(nomessage, text="Replay", width=11, bg='#fff', fg="#111", borderwidth=0, relief='flat',
                   font=('Helvetica', 10), cursor='hand2', command=clear)
btnreplay.grid(column=0, row=1, pady=10, padx=10)
btnquit = Button(nomessage, text="Quit", width=11, bg='#fff', fg="#111", borderwidth=0, relief='flat',
                 font=('Helvetica', 10), cursor='hand2', command=quitfunction)
btnquit.grid(column=1, row=1, pady=10, padx=10)

# victory message
message = Frame(root)
mes = Label(message, text=" ", font=("Helvetica", 10), fg="#111")
mes.grid(column=0, row=0, columnspan=2, ipady=10, ipadx=20)
btnreplay = Button(message, text="Replay", width=11, bg='#fff', fg="#111", borderwidth=0, relief='flat',
                   font=('Helvetica', 10), cursor='hand2', command=clear)
btnreplay.grid(column=0, row=1, pady=10, padx=10)
btnquit = Button(message, text="Quit", width=11, bg='#fff', fg="#111", borderwidth=0, relief='flat',
                 font=('Helvetica', 10), cursor='hand2', command=quitfunction)
btnquit.grid(column=1, row=1, pady=10, padx=10)

root.mainloop()
