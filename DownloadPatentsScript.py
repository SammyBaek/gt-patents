import pip

START = 0
END = 1e100

def install(packages):
    for package in packages:
        pip.main(["install", package])


def main():
    import csv, os
    csv_path = os.path.join(os.getcwd(), 'GTpatents.csv')

    counter = 0
    downloaded = 0

    with open(csv_path, newline='', encoding="utf8", errors='ignore') as csvfile:
        reader = csv.reader(csvfile, delimiter=',', quotechar='|')
        pat_ind = 0

        for row in reader:
            for i in range(len(row)):
                if (row[i] == 'dc.identifier.patentnumber'):
                    pat_ind = i
                    break
            break

        for row in reader:
            patent_number = row[pat_ind]
            counter += 1
            if counter >= START and counter <= END and patent_number != 'documentNumber':
                download_patent(patent_number)
                downloaded += 1
    print("FINSIHED!", START, "TO", (START + downloaded))


def download_patent(patent_number):
    import requests, os
    print("Downloading " + patent_number + " ...")
    link = "http://www.pat2pdf.org/pat2pdf/foo.pl?number=" + patent_number
    r = requests.get(link)
    pdf = requests.get("http://www.pat2pdf.org/patents/pat" + patent_number + ".pdf")
    path = os.path.abspath(os.path.join(os.getcwd(), 'pdf'))
    if(not os.path.exists(path)):
        os.makedirs(path)
    with open(os.path.abspath(os.path.join(os.getcwd(), 'pdf', patent_number + '.pdf')), 'wb') as file:
        file.write(pdf.content)
    print("Finished downloading " + patent_number + "!")


if __name__ == "__main__":
    packages = ["requests", "os", "csv"]
    install(packages)
    from sys import argv
    if (len(argv) > 1):
        START = int(argv[1])
    if (len(argv) > 2):
        END = int(argv[2])
    main()