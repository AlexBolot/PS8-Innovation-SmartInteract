# Définition d'un serveur réseau rudimentaire
# Ce serveur attend la connexion d'un client, pour entamer un dialogue avec lui

import socket, sys
import spacy
from spacy.matcher import Matcher
import getopt

HOST = 'localhost'
PORT = 9095


def which_det(argument):
    switcher = {
        "his": "his",
        "your": "your",
        "my": "my"
    }
    return switcher.get(argument)

def socket_read_line(soc):
    result = ""
    while 1:
        result += soc.recv(1024).decode("utf-8")
        if result.endswith("\n"):
            break
    return result

def dialogue(connexion):
    while 1:
        try:
            msgClient = socket_read_line(connexion)
            msgClient = msgClient.replace("\n", " ")
            msgClient = msgClient.replace(",", " ")
            print ("C>", msgClient)
            if msgClient == "end dialogue!!!!!!!!!!!":
                break

            msgServeur = "{ tokens : {"
            category = "default"
            numberFault = 0
            personal_question = "false"

            doc = nlp(msgClient)
            pattern = [{"POS": "DET"}, {"TEXT": "age"}]
            matcher.add("your_age", None, pattern)
            matches = matcher(doc)
            print("Total matches found:", len(matches))


            for token in doc:
                isCorrect = not token.is_oov
                if token.is_oov:
                    numberFault+=1
                if token.tag_ == "WDT" or token.tag_ == "WP" or token.tag_ == "WDT" or token.tag_ == "WRB":
                    category = token.text
                if token.pos_ != "SPACE" :
                    msgServeur += token.text + ": { Correct : " + str(isCorrect) + " } , "

            msgServeur = msgServeur[:-2]
            msgServeur += "}, category : " + category + ", "
            msgServeur += " numberFault : " + str(numberFault)

            msgServeur += ", type : { "
            msgServeur += "personalQuestion : "
            if len(matches) != 0:
                personal_question = "true"
                for match_id, start, end in matches:
                    print("Match found:", doc[start:end].text)
                    print ( "Det = " + str(which_det(doc[start].text)))
            msgServeur += personal_question + ", " + "personal : " + "age" +  " }" + "}"

            connexion.sendall((msgServeur+"\n").encode('utf-8'))
            print("S>", msgServeur)


        except (KeyboardInterrupt, Exception):
            break

args, dummy = getopt.getopt(sys.argv[1:], '', ['language='])
args = dict(args)
args.setdefault('--language', 'en')
language = args['--language']
print(language)

if language=="en":
    nlp = spacy.load('en_core_web_lg')

if language=="fr":
    nlp = spacy.load('fr_core_news_md')


matcher = Matcher(nlp.vocab)

# 1) création du socket :
mySocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# 2) liaison du socket à une adresse précise :
try:
    mySocket.bind((HOST, PORT))
    # 3) Attente de la requête de connexion d'un client :
    mySocket.listen(5)
    print ("Serveur prêt, en attente de requêtes ...")

except socket.error:
    print ("La liaison du socket à l'adresse choisie a échoué.")
    sys.exit()
while 1:


    # 4) Etablissement de la connexion :
    connexion, adresse = mySocket.accept()
    print ("Client connecté, adresse IP %s, port %s" % (adresse[0], adresse[1]))

    # 5) Dialogue avec le client :
    dialogue(connexion)

    # 6) Fermeture de la connexion :
    print ("Connexion interrompue.")
    connexion.close()

