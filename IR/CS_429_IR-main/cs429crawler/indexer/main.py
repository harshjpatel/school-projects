from bs4 import BeautifulSoup
import re
import os
import sys
import numpy as np
import pickle

html_parsing_list = list()
df_list = list()
directory = os.fsencode('htmlFiles')
ROOT_PATH = os.path.dirname(os.path.abspath('htmlFiles'))

def main():
    init = 0
    start = 0
    dict = {}
    idf_dict = {}
    tf_idf_dict = {}
    tf_idf_dict_opt_ext = {}
    query_vector_dict = {}
    cosine_similarity_dict_parent = {}
    cosine_similarity_dict_parent_2 = {}
    cosine_similarity_dict_parent_final = {}
    cosine_similarity_dict_parent_term_list = {}
    # word = "Oklahoma"

    # Tokenized-words and DF-list
    for file in os.listdir(directory):
        # if init == 1:
        #     break
        with open('iitEdu.csv', 'w') as f:
            f.truncate()
        filename = os.fsdecode(file)
        if filename.endswith(".html"):
            filePath = ROOT_PATH + "/htmlFiles/" + filename
            # print(filePath)
            HtmlFile = open(filePath, 'r', encoding='utf-8')
            source_code = HtmlFile.read()
            soup = BeautifulSoup(source_code, "html.parser")
            with open('iitEdu.csv', 'w') as f:
                for line in soup.text:
                    f.write(line)
            # upd_clear_str = re.sub("[^\w]", " ",  str(soup.text))
            # wordList = re.split('\s+', upd_clear_str)
            # print("length of wordList:= ", len(wordList))

            wordList = re.sub("[^\w]", " ",  str(soup.text)).split()
            stops = ["", "-", "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours", "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its", "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that", "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having", "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while", "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before", "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again", "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each", "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than", "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"]
            tokenized_word = [i for i in wordList if str(i).lower() not in stops]
            # print(tokenized_word)
            # print("length of tokenized_word:= ", len(tokenized_word))
            html_parsing_list.append(tokenized_word)

            # DF - list implementation
            DF = {}
            upd_DF = {}
            upd_upd_DF = {}
            for w in tokenized_word:
                if w in DF:
                    DF[w].append(w)
                else:
                    DF[w] = [w]
            for i in DF:
                upd_DF[i] = len(DF.get(i))
            for i in DF:
                upd_upd_DF[i] = upd_DF.get(i) / len(tokenized_word)
            df_list.append(upd_upd_DF)

            # Create a dictionary for all the words.
            check = str(soup.text)
            # with open('iitEdu_1.csv', 'w') as f:
            #     for line in check:
            #         f.write(line)
            # print("html_parsing_list[init]:= ", html_parsing_list[init])
            # if word in check:
            #     print(">> Oklahama word is present!")
            for item in html_parsing_list[init]:
                if item in check:
                    if item not in dict:
                        dict[item] = []
                    if item in dict and (init + 1) not in dict.get(item):
                        dict[item].append(init + 1)

            # Convert Index pickling of dictionary index pages pickle files.
            fileName = "save_" + str(init) + ".pkl"
            if not os.path.isfile(os.path.join('picklefiles', fileName)):
                with open(os.path.join('picklefiles', fileName), 'wb') as f:
                    pickle.dump(dict, f, protocol=pickle.HIGHEST_PROTOCOL)
            else:
                os.remove(os.path.join('picklefiles', fileName))
                with open(os.path.join('picklefiles', fileName), 'wb') as f:
                    pickle.dump(dict, f, protocol=pickle.HIGHEST_PROTOCOL)

        init += 1


    # if word in dict:
    #     print("The word is in the list!")
    # print("dict score of term 'Binary':= ", dict["Binary"])
    # print("dict score of term 'Binary':= ", dict["Oshawa"])

    # print("df_list:= ", df_list[18])

    # Pickle file - html_parsing_list list
    if not os.path.isfile('picklefiles/html_parsing_list_pkl.pkl'):
        with open('picklefiles/html_parsing_list_pkl.pkl', 'wb') as f:
            pickle.dump(html_parsing_list, f, protocol=pickle.HIGHEST_PROTOCOL)
    else:
        os.remove('picklefiles/html_parsing_list_pkl.pkl')
        with open('picklefiles/html_parsing_list_pkl.pkl', 'wb') as f:
            pickle.dump(html_parsing_list, f, protocol=pickle.HIGHEST_PROTOCOL)

    # IDF_score logic
    for key, values in dict.items():
        idf_score = np.log(len(html_parsing_list) / len(values))
        if key not in idf_dict:
            idf_dict[key] = list()
        for value in values:
            idf_dict[key].append([value, idf_score])
    # print("idf_dict score of term 'Binary':= ", idf_dict["Binary"])
    # print("idf_dict score of term 'Binary':= ", idf_dict["Oshawa"])

    # Pickle file - IDF
    if not os.path.isfile('picklefiles/idf.pkl'):
        with open('picklefiles/idf.pkl', 'wb') as f:
            pickle.dump(idf_dict, f, protocol=pickle.HIGHEST_PROTOCOL)
    else:
        os.remove('picklefiles/idf.pkl')
        with open('picklefiles/idf.pkl', 'wb') as f:
            pickle.dump(idf_dict, f, protocol=pickle.HIGHEST_PROTOCOL)

    # Find TF-IDF score
    init_token = 0
    for token_item in range(len(html_parsing_list)):
        # if init_token == 1:
        #     break
        for item in df_list[token_item]:
            if item not in tf_idf_dict:
                tf_idf_dict[item] = {}
            if item in tf_idf_dict:
                tf_idf_dict_opt = {}
                doc_ids = [docs[0] for docs in idf_dict[item]]
                doc_ids_idf_score = [docs[1] for docs in idf_dict[item]]
                # print("item:= ", item)
                # print("doc_ids:= ", doc_ids)
                # print("doc_ids_idf_score:= ", doc_ids_idf_score)
                count = 0
                for number in doc_ids:
                    if number not in tf_idf_dict_opt:
                        tf_idf_dict_opt[number] = []
                    if number not in tf_idf_dict_opt_ext:
                        tf_idf_dict_opt_ext[number] = []
                    if number in tf_idf_dict_opt:
                        # print("doc_ids_idf_score[count]:= ", doc_ids_idf_score[count])
                        # print("df_list[number - 1][item]:= ", df_list[number - 1][item])
                        tf_idf_dict_opt[number].append(doc_ids_idf_score[count] * df_list[number - 1][item])
                        tf_idf_dict_opt_ext[number].append(doc_ids_idf_score[count] * df_list[number - 1][item])
                        count = count + 1
                tf_idf_dict[item] = tf_idf_dict_opt
        init_token += 1
        #elementsArticles
    # print("tf_idf_dict_opt_ext:= ", tf_idf_dict_opt_ext)
    # print("tf_idf_dict score of term 'Binary':= ", tf_idf_dict["Binary"])
    # print("tf_idf_dict score of term 'Oshawa':= ", tf_idf_dict["Oshawa"])
    # print("tf_idf_dict score of term 'Vancouver':= ", tf_idf_dict["Vancouver"])
    # print("tf_idf_dict_opt_ext score:= ", len(tf_idf_dict_opt_ext))

    # Pickle file - TF/IDF
    if not os.path.isfile('picklefiles/td_idf.pkl'):
        with open('picklefiles/td_idf.pkl', 'wb') as f:
            pickle.dump(tf_idf_dict, f, protocol=pickle.HIGHEST_PROTOCOL)
    else:
        os.remove('picklefiles/td_idf.pkl')
        with open('picklefiles/td_idf.pkl', 'wb') as f:
            pickle.dump(tf_idf_dict, f, protocol=pickle.HIGHEST_PROTOCOL)

    # Pickle file - TF_IDF_OPT
    if not os.path.isfile('picklefiles/tf_idf_opt.pkl'):
        with open('picklefiles/tf_idf_opt.pkl', 'wb') as f:
            pickle.dump(tf_idf_dict_opt_ext, f, protocol=pickle.HIGHEST_PROTOCOL)
    else:
        os.remove('picklefiles/tf_idf_opt.pkl')
        with open('picklefiles/tf_idf_opt.pkl', 'wb') as f:
            pickle.dump(tf_idf_dict_opt_ext, f, protocol=pickle.HIGHEST_PROTOCOL)

    # The vector should be a dict with query terms as keys and IDF scores as values.
    query_to_check = "Oshawa Vancouver"
    stops = ["", "-", "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours", "yourself",
             "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its", "itself",
             "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that", "these",
             "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having", "do",
             "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
             "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
             "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
             "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
             "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
             "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"]
    data = re.sub('[^\w]|_', ' ', query_to_check)  # only keep numbers and letters and spaces
    # data = data.lower()
    data = re.sub(r'[^\x00-\x7f]', r'', data)  # remove non ascii texts
    tokens = [data_upt for data_upt in data.split(' ') if data_upt]  # split the words and remove empty words
    tokens = [word for word in tokens if word.lower() not in stops]
    for token in tokens:
        doc_ids_idf_score = [idf_dict[token][0][1]]
        query_vector_dict[token] = doc_ids_idf_score
    print("sample Query vector score:= ", query_vector_dict)

    # Cosine similarity
    # The result of the dot product between the query vector and the documents (per term) need to be converted into a final sorted list of pairs containing the document identifier and score
    # The sorting is based on highest score to lowest.
    terms = [docs for docs in query_vector_dict]
    terms_idf_val = [query_vector_dict.get(docs)[0] for docs in query_vector_dict]
    for term_num in range(len(terms)):
        doc_length = []
        cosine_similarity_dict_clone_res = {}
        dict_keys = list(tf_idf_dict[terms[term_num]].keys())
        cosine_similarity_dict = {}
        wt = terms_idf_val[term_num]
        for idx in range(len(html_parsing_list)):
            if idx not in dict_keys:
                cosine_similarity_dict[idx] = 0
            else:
                cosine_similarity_dict[idx] = terms_idf_val[0] * tf_idf_dict[terms[term_num]].get(idx)[0]
                doc_length.append(tf_idf_dict_opt_ext.get(idx)[0])
        doc_square = [i ** 2 for i in doc_length]
        doc_length_sqrt_val = pow(sum(doc_square), 0.5)
        for key, values in cosine_similarity_dict.items():
            cosine_similarity_dict_clone_res[key] = cosine_similarity_dict.get(key) / doc_length_sqrt_val
        for key, values in cosine_similarity_dict_clone_res.items():
            if key not in cosine_similarity_dict_parent and key != 0:
                cosine_similarity_dict_parent[key] = []
            if key in cosine_similarity_dict_parent:
             cosine_similarity_dict_parent[key].append(values)
        for key, values in cosine_similarity_dict_parent.items():
            if key not in cosine_similarity_dict_parent:
                cosine_similarity_dict_parent_2[key] = 0
            if key in cosine_similarity_dict_parent:
                cosine_similarity_dict_parent_2[key] = sum(cosine_similarity_dict_parent.get(key))
        cosine_similarity_dict_parent_term_list[terms[term_num]] = cosine_similarity_dict_clone_res
    print("cosine_similarity_dict_parent_term_list:= ", cosine_similarity_dict_parent_term_list)
    cosine_similarity_dict_parent_final = sorted(cosine_similarity_dict_parent_2.items(), key=lambda t: t[1],
                                                 reverse=True)

    # "document identifier, score" for query and document pages for combine query vector and document vector.
    print("Sorted cosine_similarity_dict_parent_final:= ", cosine_similarity_dict_parent_final)


if __name__ == '__main__':
    main()