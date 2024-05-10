z = int(input("실행하시려면 1을 눌러주세요: "))

while z == 1:
    a = float(input("키 입력(0 누르면 종료): "))
    if a == 0:
        break
    b = float(input("현재 몸무게 입력: "))
    c = float(input("목표 몸무게 입력: "))
    d = int(input("나이 입력: "))
    e = int(input("목표 기간(일수) 입력: "))

    f = 66.47 + (13.75 * b) + (5 * a) - (6.76 * d)
    g = f * 0.2
    h = (f + g) * e
    i = (b - c) * 7800
    j = (h - i) / e

    print("하루 섭취 가능 열량: {:.2f} kcal".format(j))

    z = input("다시 실행하려면 1을 눌러주세요(종료하려면 다른 숫자 입력): ")
