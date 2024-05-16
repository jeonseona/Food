import pandas as pd
import numpy as np
import sys
import warnings; warnings.filterwarnings('ignore')
from ast import literal_eval
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from tmdbv3api import Movie, TMDb

def find_sim_movie(df, sorted_ind, title_name, top_n=10):
    # 매개변수로 입력된 title_name의 영화정보를 읽는다.
    title_movie = df[df['title'] == title_name]
    title_index = title_movie.index.values
    
    similar_indexes = sorted_ind[title_index, :(top_n*2)]
    # 추출된 벡터를 1차원 array로 변경
    similar_indexes = similar_indexes.reshape(-1)
    
    # 기준 영화 인덱스 제외
    similar_indexes = similar_indexes[similar_indexes != title_index]
    
    return df.iloc[similar_indexes].sort_values('weighted_vote', ascending=False)[:top_n]

def make_genre_mat(df):
    df['genres_literal'] = df['genres'].apply(lambda x : (' ').join(x))
    count_vect = CountVectorizer(min_df=0., ngram_range=(1,2)) # min_df - 최소 데이터 빈도, ngram_range - 단어의 묶음을 1개 또는 2개로 지정.
    genre_mat = count_vect.fit_transform(df['genres_literal'])   
    genre_sim = cosine_similarity(genre_mat, genre_mat)
    genre_sim_sorted_ind = genre_sim.argsort()[:, ::-1]
    
    return genre_sim_sorted_ind

# 영화 포스터 추가 함수
def add_movie_poster(movies_df, similar_df):
    movie_indices = similar_df.index
 
    images = []
    titles = []
    for i in movie_indices:
        id = movies_df['id'].iloc[i]
        details = movie.details(id)

        image_path = details['poster_path']
        if image_path:
            image_path = 'https://image.tmdb.org/t/p/w500' + image_path
        else:
            image_path = 'no_image.jpg'

        images.append(image_path)
        titles.append(details['title'])

    similar_df['poster_path'] = images
    similar_df['title'] = titles
    
    return similar_df  
# End of add_movie_poster()
    
# 영화 추천 메인 메소드
def movie_recommend(movies_df, title):

    genre_sim = make_genre_mat(movies_df)
    
    similar_movies = find_sim_movie(movies_df, genre_sim, title, 10)

    similar_movies = add_movie_poster(movies_df, similar_movies)    
     
    for row in similar_movies.index:
        print(similar_movies.loc[row, 'title'], ',', similar_movies.loc[row, 'vote_count'], ',',
              similar_movies.loc[row, 'vote_average'], ',', similar_movies.loc[row, 'weighted_vote'], '',
              similar_movies.loc[row, 'poster_path'])
    
# 실행
#print ('Movie title=', sys.argv[1])
movie = Movie()
tmdb = TMDb()
tmdb.api_key = '96a167172711d4d7f145c5bb8ce8604f'
tmdb.language = 'ko-KR'

movies_df = pd.read_pickle('movies_df.pkl')
movie_recommend(movies_df, sys.argv[1])