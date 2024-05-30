import sys
import pandas as pd
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import TfidfVectorizer

def find_words_with_excluded_ingredients(food_data, excluded_ingredients):
    except_list = []
    for ingredients in food_data['RECIPEINGREDIENTPARTS']:
        words = ingredients.split()
        for word in words:
            for ingredient in excluded_ingredients:
                if ingredient.lower() in word.lower():
                    except_list.append(word)
    return except_list

def filter_food_data(food_data, except_list, calories_threshold):
    filtered_food_data = food_data.copy()
    for ingredient in except_list:
        # 'RECIPEINGREDIENTPARTS' 열에서 해당 재료를 포함하지 않는 행만을 선택하여 필터링
        filtered_food_data = filtered_food_data[~filtered_food_data['RECIPEINGREDIENTPARTS'].str.contains(ingredient, case=False, regex=False)]
      
    filtered_food_data = filtered_food_data[filtered_food_data['CALORIES'] < calories_threshold]
    return filtered_food_data

def get_recommendations(user_keywords, tfidf_vectorizer, tfidf_matrix):
    user_tfidf_matrix = tfidf_vectorizer.transform([' '.join(user_keywords)])
    cosine_sim = cosine_similarity(user_tfidf_matrix, tfidf_matrix)
    return cosine_sim[0]

def calculate_nutrient_similarity(user_nutrient_needs, food_nutrients):
    user_nutrient_vector = np.array(user_nutrient_needs).reshape(1, -1)
    nutrient_sim = cosine_similarity(user_nutrient_vector, food_nutrients)
    return nutrient_sim[0]

def get_final_recommendations(keywords_similarities, nutrient_similarities, food_data, user_calories_threshold):
    combined_similarities = (np.array(keywords_similarities) + np.array(nutrient_similarities)) / 2
    sim_scores = list(enumerate(combined_similarities))
    sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=True)
    food_indices = [idx[0] for idx in sim_scores if food_data.iloc[idx[0]]['CALORIES'] < user_calories_threshold]
    recommendations = food_data.iloc[food_indices][['IDX', 'NAME', 'CALORIES', 'IMAGES']]
    
    for _, row in recommendations.head(30).iterrows():
        print(f"{row['IDX']}@{row['NAME']}@{row['CALORIES']} kcal@{row['IMAGES']}")

if __name__ == "__main__":
    selected_keywords = sys.argv[1]
    exclude_ingredients = sys.argv[2]
    
    diet_calory = float(sys.argv[3]) * 0.35
    
    temp = list(sys.argv[4].split(","))
    
    nutrient_choice = [float(x) for x in temp]

    food = pd.read_pickle('./food.pkl')

    except_list = find_words_with_excluded_ingredients(food, exclude_ingredients)
    unique_except_list = list(set(except_list))
    filtered_food = filter_food_data(food, unique_except_list, diet_calory)

    tfidf = TfidfVectorizer(stop_words='english')
    tfidf_matrix = tfidf.fit_transform(filtered_food['KEYWORDS'])
    keywords_similarities = get_recommendations(selected_keywords, tfidf, tfidf_matrix)

    food_nutrients = filtered_food[['CARBOHYDRATECONTENT', 'PROTEINCONTENT', 'FATCONTENT']].values
    nutrient_similarities = calculate_nutrient_similarity(nutrient_choice, food_nutrients)

    get_final_recommendations(keywords_similarities, nutrient_similarities, filtered_food, diet_calory)
