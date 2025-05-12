from flask import Flask, request, jsonify, make_response
import pickle
import os
import re
from pathlib import Path

app = Flask(__name__)

# TF-IDF dict objects <- To use pickle files
main_res_dict_tf_idf = {}
tf_idf_dict = {}
dict_new_tf_idf = {}

# IDF dict objects <- To use pickle files
idf_dict = {}
dict_new_idf = {}

# TF_IDF_OPT dict objects <- To use pickle files
main_res_dict_tf_idf_opt = {}
tf_idf_dict_opt_ext = {}
dict_new_tf_idf_opt = {}

# Query vector objects <- To use pickle files
query_vector_dict = {}
dict_new_inverted_index = {}
main_res_dict_inverted_index = {}

# Cosine similarity objects -> To get similarity score.
cosine_similarity_dict_parent = {}
cosine_similarity_dict_parent_2 = {}
cosine_similarity_dict_parent_final = {}
cosine_similarity_dict_parent_term_list = {}

# html_parsing_list object <- To use pickle files
html_parsing_list = list()

@app.route('/', methods=['POST'])
def process_json():
    global cosine_similarity_dict_parent_final
    content_type = request.headers.get('Content-Type')
    if (content_type == 'application/json'):
        query_list = list()
        json = request.json
        # query_1 = json['query1']
        # print("query_1:= ", query_1)
        for idx in range(len(json)):
            init = idx + 1
            queryName = "query" + str(init)
            query_list.append(json[queryName])
        print("Query list(from Json format):= ", query_list)

        # Using block-sorting algorithm, merged/combined together for the final one inverted index.
        myDir = Path('picklefiles/')
        fileNames = [file.name for file in myDir.iterdir() if file.name.startswith('save')]
        for file in fileNames:
            with open(os.path.join(myDir, file), 'rb') as fileName:
                dict_new_inverted_index = pickle.load(fileName)
                for k, v in dict_new_inverted_index.items():
                    try:
                        main_res_dict_inverted_index[k].extend(v)
                    except KeyError:
                        main_res_dict_inverted_index[k] = v
        res_main_res_dict = {key: list(set(value)) for key, value in main_res_dict_inverted_index.items()}
        print("Invrted index length: = ", len(res_main_res_dict))

        # TF-IDF file
        if os.path.isfile('picklefiles/html_parsing_list_pkl.pkl'):
            with open('picklefiles/html_parsing_list_pkl.pkl', 'rb') as fileName:
                html_parsing_list = pickle.load(fileName)

        # TF-IDF file to tf_idf_dict
        if os.path.isfile('picklefiles/td_idf.pkl'):
            with open('picklefiles/td_idf.pkl', 'rb') as fileName:
                dict_new_tf_idf = pickle.load(fileName)
                for k, v in dict_new_tf_idf.items():
                    try:
                        tf_idf_dict[k].extend(v)
                    except KeyError:
                        tf_idf_dict[k] = v
        # tf_idf_dict = {key: list(set(value)) for key, value in main_res_dict_tf_idf.items()}

        # IDF file to idf_dict
        if os.path.isfile('picklefiles/idf.pkl'):
            with open('picklefiles/idf.pkl', 'rb') as fileName:
                dict_new_idf = pickle.load(fileName)
                for k, v in dict_new_idf.items():
                    try:
                        idf_dict[k].extend(v)
                    except KeyError:
                        idf_dict[k] = v
        #
        # # TF_IDF_OPT dict to tf_idf_dict_opt_ext
        if os.path.isfile('picklefiles/tf_idf_opt.pkl'):
            with open('picklefiles/tf_idf_opt.pkl', 'rb') as fileName:
                dict_new_tf_idf_opt = pickle.load(fileName)
                for k, v in dict_new_tf_idf_opt.items():
                    try:
                        tf_idf_dict_opt_ext[k].extend(v)
                    except KeyError:
                        tf_idf_dict_opt_ext[k] = v
        #
        # # Query vector and similarity
        for idx in range(len(query_list)):
            stops = ["", "-", "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
                     "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
                     "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
                     "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
                     "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until",
                     "while", "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during",
                     "before", "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under",
                     "again", "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both",
                     "each", "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so",
                     "than", "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"]
            query_to_check = query_list[idx]
            data = re.sub('[^\w]|_', ' ', query_to_check)  # only keep numbers and letters and spaces
            # data = data.lower()
            data = re.sub(r'[^\x00-\x7f]', r'', data)  # remove non ascii texts
            tokens = [data_upt for data_upt in data.split(' ') if data_upt]  # split the words and remove empty words
            tokens = [word for word in tokens if word.lower() not in stops]
            for token in tokens:
                doc_ids_idf_score = [idf_dict[token][0][1]]
                query_vector_dict[token] = doc_ids_idf_score
        #
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
                print("\n\n\nTop-K ranked results(Query word -> similarity result):= ",cosine_similarity_dict_parent_term_list)
                cosine_similarity_dict_parent_final = sorted(cosine_similarity_dict_parent_2.items(), key=lambda t: t[1],
                                                             reverse=True)
                print("Top-K ranked results(Top-K Document Numbers):= ", cosine_similarity_dict_parent_final)
        # return json
        jsonify("The result is given below (HTML page saved and their K-top results using similarity")
        return jsonify(cosine_similarity_dict_parent_final), 200
    else:
        return 'Content-Type not supported!'

if __name__ == '__main__':
    app.run(debug=True)

