import textract


def parser(loc):
    filename = loc
    print(loc)
    text = ""
    text = textract.process(filename, encoding='utf_8')
    # print(text)
    return str(text.decode()).replace('\r', '').replace('\n', ' ').replace('\f', '')
