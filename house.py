# 주택매물관련


# !pip install pandas
# !pip install requests
import pandas as pd
import requests
import json


def get_sido_info():
    down_url = 'https://new.land.naver.com/api/regions/list?cortarNo=0000000000'
    r = requests.get(down_url, data={"sameAddressGroup": "false"}, headers={
        "Accept-Encoding": "gzip",
        "Host": "new.land.naver.com",
        "Referer": "https://new.land.naver.com/complexes/102378?ms=37.5018495,127.0438028,16&a=APT&b=A1&e=RETAIL",
        "Sec-Fetch-Dest": "empty",
        "Sec-Fetch-Mode": "cors",
        "Sec-Fetch-Site": "same-origin",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36"
    })
    r.encoding = "utf-8-sig"
    temp1 = json.loads(r.text)
    temp1 = list(pd.DataFrame(temp1["regionList"])["cortarNo"])
    return temp1


def get_gungu_info(sido_code):
    down_url = 'https://new.land.naver.com/api/regions/list?cortarNo=' + sido_code
    r = requests.get(down_url, data={"sameAddressGroup": "false"}, headers={
        "Accept-Encoding": "gzip",
        "Host": "new.land.naver.com",
        "Referer": "https://new.land.naver.com/complexes/102378?ms=37.5018495,127.0438028,16&a=APT&b=A1&e=RETAIL",
        "Sec-Fetch-Dest": "empty",
        "Sec-Fetch-Mode": "cors",
        "Sec-Fetch-Site": "same-origin",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36"
    })
    r.encoding = "utf-8-sig"
    temp2 = json.loads(r.text)
    temp2 = list(pd.DataFrame(temp2['regionList'])["cortarNo"])
    return temp2


def get_dong_info(gungu_code):
    down_url = 'https://new.land.naver.com/api/regions/list?cortarNo=' + gungu_code
    r = requests.get(down_url, data={"sameAddressGroup": "false"}, headers={
        "Accept-Encoding": "gzip",
        "Host": "new.land.naver.com",
        "Referer": "https://new.land.naver.com/complexes/102378?ms=37.5018495,127.0438028,16&a=APT&b=A1&e=RETAIL",
        "Sec-Fetch-Dest": "empty",
        "Sec-Fetch-Mode": "cors",
        "Sec-Fetch-Site": "same-origin",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36"
    })
    r.encoding = "utf-8-sig"
    temp3 = json.loads(r.text)
    temp3 = list(pd.DataFrame(temp3['regionList'])["cortarNo"])
    return temp3


def get_apt_info(apt_code):
    down_url = 'https://new.land.naver.com/api/complexes/overview/' + apt_code + '?complexNo=' + apt_code + ''
    r = requests.get(down_url, data={"sameAddressGroup": "false"}, headers={
        "Accept-Encoding": "gzip",
        "Host": "new.land.naver.com",
        "Referer": "https://new.land.naver.com/complexes/" + apt_code + "?ms=37.482968,127.0634,16&a=APT&b=A1&e=RETAIL",
        "Sec-Fetch-Dest": "empty",
        "Sec-Fetch-Mode": "cors",
        "Sec-Fetch-Site": "same-origin",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36"
    })
    r.encoding = "utf-8-sig"
    temp4 = json.loads(r.text)
    return temp4


def apt_adress(apt_code):
    down_url = 'https://new.land.naver.com/api/complexes/' + apt_code + '?complexNo=' + apt_code + '&initial=Y'

    r = requests.get(down_url, data={"sameAddressGroup": "false"}, headers={
        "Accept-Encoding": "gzip",
        "Host": "new.land.naver.com",
        "authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IlJFQUxFU1RBVEUiLCJpYXQiOjE2Nzk5NzgzODAsImV4cCI6MTY3OTk4OTE4MH0.hiFEBAL0P_0u2LoB350JHAKNos0wRhm7Jb12kMDnbjg",
        "Referer": "https://new.land.naver.com/complexes/" + apt_code + "?ms=37.496693,127.078408,16&a=APT:PRE&e=RETAIL",
        "Sec-Fetch-Dest": "empty",
        "Sec-Fetch-Mode": "cors",
        "Sec-Fetch-Site": "same-origin",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36"
    })
    r.encoding = "utf-8-sig"
    temp5 = json.loads(r.text)

    address = temp5['complex']["cortarAddress"].split(' ')

    return address


def get_apt_loca(apt_code):
    # down_url  = 'https://new.land.naver.com/api/complexes/overview/'+apt_code+'?complexNo='+apt_code+''
    # down_url  = 'https://m.land.naver.com/cluster/clusterList?view=atcl&rletTpCd=OBYG%3AABYG%3AOPST%3AAPT&tradTpCd=A1%3AB1&z=18&lat=37.496437&lon=127.077115&btm=37.495124&lft=127.0749451&top=37.49775&rgt=127.0792849&pCortarNo=18_1168010300&addon=COMPLEX&bAddon=COMPLEX&isOnlyIsale=false'

    down_url = 'https://new.land.naver.com/api/complexes/overview/' + apt_code + '?complexNo=' + apt_code + '&isClickedMarker=true'

    # down_url  = 'https://new.land.naver.com/api/articles/complex/8928?realEstateType=APT%3APRE&tradeType=&tag=%3A%3A%3A%3A%3A%3A%3A%3A&rentPriceMin=0&rentPriceMax=900000000&priceMin=0&priceMax=900000000&areaMin=0&areaMax=900000000&oldBuildYears&recentlyBuildYears&minHouseHoldCount&maxHouseHoldCount&showArticle=false&sameAddressGroup=false&minMaintenanceCost&maxMaintenanceCost&priceType=RETAIL&directions=&page=1&complexNo=8928&buildingNos=&areaNos=&type=list&order=rank'
    r = requests.get(down_url, data={"sameAddressGroup": "false"}, headers={
        "Accept-Encoding": "gzip",
        "authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IlJFQUxFU1RBVEUiLCJpYXQiOjE2Nzk5NzgzODAsImV4cCI6MTY3OTk4OTE4MH0.hiFEBAL0P_0u2LoB350JHAKNos0wRhm7Jb12kMDnbjg",
        "Host": "new.land.naver.com",
        "Referer": "https://new.land.naver.com/complexes/" + apt_code + "?ms=37.482968,127.0634,16&a=APT&b=A1&e=RETAIL",
        "Sec-Fetch-Dest": "empty",
        "Sec-Fetch-Mode": "cors",
        "Sec-Fetch-Site": "same-origin",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36"
    })
    r.encoding = "utf-8-sig"
    temp6 = json.loads(r.text)
    return temp6


def apt_adress1(apt_code):
    down_url = 'https://new.land.naver.com/api/complexes/' + apt_code + '?complexNo=' + apt_code + '&initial=Y'

    r = requests.get(down_url, data={"sameAddressGroup": "false"}, headers={
        "Accept-Encoding": "gzip",
        "Host": "new.land.naver.com",
        "authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IlJFQUxFU1RBVEUiLCJpYXQiOjE2Nzk5NzgzODAsImV4cCI6MTY3OTk4OTE4MH0.hiFEBAL0P_0u2LoB350JHAKNos0wRhm7Jb12kMDnbjg",
        "Referer": "https://new.land.naver.com/complexes/" + apt_code + "?ms=37.496693,127.078408,16&a=APT:PRE&e=RETAIL",
        "Sec-Fetch-Dest": "empty",
        "Sec-Fetch-Mode": "cors",
        "Sec-Fetch-Site": "same-origin",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36"
    })
    r.encoding = "utf-8-sig"
    temp7 = json.loads(r.text)

    address = temp7['complex']["cortarAddress"]

    return address


def get_apt_info(apt_code):
    down_url = 'https://new.land.naver.com/api/complexes/overview/' + apt_code + '?complexNo=' + apt_code + ''
    # down_url  = 'https://m.land.naver.com/cluster/clusterList?view=atcl&rletTpCd=OBYG%3AABYG%3AOPST%3AAPT&tradTpCd=A1%3AB1&z=18&lat=37.496437&lon=127.077115&btm=37.495124&lft=127.0749451&top=37.49775&rgt=127.0792849&pCortarNo=18_1168010300&addon=COMPLEX&bAddon=COMPLEX&isOnlyIsale=false'
    r = requests.get(down_url, data={"sameAddressGroup": "false"}, headers={
        "Accept-Encoding": "gzip",
        "Host": "new.land.naver.com",
        "Referer": "https://new.land.naver.com/complexes/" + apt_code + "?ms=37.482968,127.0634,16&a=APT&b=A1&e=RETAIL",
        "Sec-Fetch-Dest": "empty",
        "Sec-Fetch-Mode": "cors",
        "Sec-Fetch-Site": "same-origin",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36"
    })
    r.encoding = "utf-8-sig"
    temp8 = json.loads(r.text)
    return temp8


def get_apt_loca(apt_code):
    # down_url  = 'https://new.land.naver.com/api/complexes/overview/'+apt_code+'?complexNo='+apt_code+''
    # down_url  = 'https://m.land.naver.com/cluster/clusterList?view=atcl&rletTpCd=OBYG%3AABYG%3AOPST%3AAPT&tradTpCd=A1%3AB1&z=18&lat=37.496437&lon=127.077115&btm=37.495124&lft=127.0749451&top=37.49775&rgt=127.0792849&pCortarNo=18_1168010300&addon=COMPLEX&bAddon=COMPLEX&isOnlyIsale=false'

    down_url = 'https://new.land.naver.com/api/complexes/overview/' + apt_code + '?complexNo=' + apt_code + '&isClickedMarker=true'

    # down_url  = 'https://new.land.naver.com/api/articles/complex/8928?realEstateType=APT%3APRE&tradeType=&tag=%3A%3A%3A%3A%3A%3A%3A%3A&rentPriceMin=0&rentPriceMax=900000000&priceMin=0&priceMax=900000000&areaMin=0&areaMax=900000000&oldBuildYears&recentlyBuildYears&minHouseHoldCount&maxHouseHoldCount&showArticle=false&sameAddressGroup=false&minMaintenanceCost&maxMaintenanceCost&priceType=RETAIL&directions=&page=1&complexNo=8928&buildingNos=&areaNos=&type=list&order=rank'
    r = requests.get(down_url, data={"sameAddressGroup": "false"}, headers={
        "Accept-Encoding": "gzip",
        "authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IlJFQUxFU1RBVEUiLCJpYXQiOjE2Nzk5NzgzODAsImV4cCI6MTY3OTk4OTE4MH0.hiFEBAL0P_0u2LoB350JHAKNos0wRhm7Jb12kMDnbjg",
        "Host": "new.land.naver.com",
        "Referer": "https://new.land.naver.com/complexes/" + apt_code + "?ms=37.482968,127.0634,16&a=APT&b=A1&e=RETAIL",
        "Sec-Fetch-Dest": "empty",
        "Sec-Fetch-Mode": "cors",
        "Sec-Fetch-Site": "same-origin",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36"
    })
    r.encoding = "utf-8-sig"
    temp9 = json.loads(r.text)
    return temp9


def apt_price(apt_code):
    # p_num=temp["complexPyeongDetailList"][index]["pyeongNo"]
    # https://new.land.naver.com/api/complexes/114740/prices/real?complexNo=114740&tradeType=A1&year=5&priceChartChange=false&areaNo=0&type=table
    # down_url = 'https://new.land.naver.com/api/complexes/8928/prices/real?complexNo=8928&tradeType=A1&year=5&priceChartChange=false&areaNo=2&type=table'
    down_url = 'https://new.land.naver.com/api/complexes/' + apt_code + '/prices/real?complexNo=' + apt_code + '&tradeType=A1&year=5&priceChartChange=false&areaNo=0&type=table'
    r = requests.get(down_url, headers={
        "Accept-Encoding": "gzip",
        "authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IlJFQUxFU1RBVEUiLCJpYXQiOjE2Nzk5NzgzODAsImV4cCI6MTY3OTk4OTE4MH0.hiFEBAL0P_0u2LoB350JHAKNos0wRhm7Jb12kMDnbjg",
        "Host": "new.land.naver.com",
        "Referer": "https://new.land.naver.com/complexes/" + apt_code + "?ms=37.4966933,127.0796005,16&a=APT:PRE&e=RETAIL",
        "Sec-Fetch-Dest": "empty",
        "Sec-Fetch-Mode": "cors",
        "Sec-Fetch-Site": "same-origin",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36"
    })
    r.encoding = "utf-8-sig"
    temp_price = json.loads(r.text)
    return temp_price


def get_jutack_list(dong_code):
    down_url = 'https://new.land.naver.com/api/articles?cortarNo=' + dong_code + '&order=rank&realEstateType=VL%3ADDDGG%3AJWJT%3ASGJT%3AHOJT&tradeType=&tag=%3A%3A%3A%3A%3A%3A%3A%3A&rentPriceMin=0&rentPriceMax=900000000&priceMin=0&priceMax=900000000&areaMin=0&areaMax=900000000&oldBuildYears&recentlyBuildYears&minHouseHoldCount&maxHouseHoldCount&showArticle=false&sameAddressGroup=false&minMaintenanceCost&maxMaintenanceCost&priceType=RETAIL&directions=&page=1&articleState'
    r = requests.get(down_url, data={"sameAddressGroup": "false"}, headers={
        "Accept-Encoding": "gzip",
        "authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IlJFQUxFU1RBVEUiLCJpYXQiOjE2OTA5NTU2NjQsImV4cCI6MTY5MDk2NjQ2NH0.7vaA8L4HznI0xUL-lz9QD8wUQ5NAcqPtzbML2ZhKiA0",
        "Host": "new.land.naver.com",
        "Referer": "https://new.land.naver.com/complexes/102378?ms=37.5018495,127.0438028,16&a=APT&b=A1&e=RETAIL",
        "Sec-Fetch-Dest": "empty",
        "Sec-Fetch-Mode": "cors",
        "Sec-Fetch-Site": "same-origin",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36"
    })

    r.encoding = "utf-8-sig"

    temp10 = json.loads(r.text)
    temp10 = list(pd.DataFrame(temp10['articleList']))
    if not temp10:
        # ["articleNo"]
        return temp10
    else:
        temp21 = json.loads(r.text)
        temp21 = list(pd.DataFrame(temp21['articleList'])["articleNo"])
        return temp21


def get_jutack_info(apt_code):
    # down_url  = 'https://new.land.naver.com/api/articles/'+apt_code+'?complexNo='
    down_url = 'https://new.land.naver.com/api/articles/' + apt_code + '?complex'
    # down_url  = 'https://new.land.naver.com/api/articles/'+apt_code+'?complexNo='
    # down_url  = 'https://new.land.naver.com/api/articles?zoom=19&leftLon=127.7320298&rightLon=127.7365359&topLat=37.8609173&bottomLat=37.8593333&order=rank&realEstateType=VL&tradeType=A1%3AB1&tag=%3A%3A%3A%3A%3A%3A%3A%3A&rentPriceMin=0&rentPriceMax=900000000&priceMin=0&priceMax=900000000&areaMin=0&areaMax=900000000&oldBuildYears&recentlyBuildYears&minHouseHoldCount&maxHouseHoldCount&showArticle=false&sameAddressGroup=false&minMaintenanceCost&maxMaintenanceCost&priceType=RETAIL&directions=&page=1&articleState'
    # down_url  = 'https://new.land.naver.com/api/articles?markerId=302203320323&markerType=LGEOHASH_MIX_ARTICLE&prevScrollTop=0&order=rank&realEstateType=VL%3ADDDGG%3AJWJT%3ASGJT%3AHOJT&tradeType=&tag=%3A%3A%3A%3A%3A%3A%3A%3A&rentPriceMin=0&rentPriceMax=900000000&priceMin=0&priceMax=900000000&areaMin=0&areaMax=900000000&oldBuildYears&recentlyBuildYears&minHouseHoldCount&maxHouseHoldCount&showArticle=false&sameAddressGroup=false&minMaintenanceCost&maxMaintenanceCost&priceType=RETAIL&directions=&page=1&articleState'

    # down_url  = 'https://m.land.naver.com/cluster/clusterList?view=atcl&rletTpCd=OBYG%3AABYG%3AOPST%3AAPT&tradTpCd=A1%3AB1&z=18&lat=37.496437&lon=127.077115&btm=37.495124&lft=127.0749451&top=37.49775&rgt=127.0792849&pCortarNo=18_1168010300&addon=COMPLEX&bAddon=COMPLEX&isOnlyIsale=false'
    r = requests.get(down_url, data={"sameAddressGroup": "false"}, headers={
        "Accept-Encoding": "gzip",
        "authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IlJFQUxFU1RBVEUiLCJpYXQiOjE2Nzk5NzgzODAsImV4cCI6MTY3OTk4OTE4MH0.hiFEBAL0P_0u2LoB350JHAKNos0wRhm7Jb12kMDnbjg",
        "Host": "new.land.naver.com",
        "Referer": "https://new.land.naver.com/houses?ms=37.7693,128.8883,16&a=VL&b=A1:B1&e=RETAIL",
        "Sec-Fetch-Dest": "empty",
        "Sec-Fetch-Mode": "cors",
        "Sec-Fetch-Site": "same-origin",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36"
    })
    r.encoding = "utf-8-sig"
    try:
        temp11 = json.loads(r.text)
    except json.JSONDecodeError as e:
        print("info error:", e)
        temp11 = {}

    return temp11


def rm(input_string):
    characters_to_remove = ["억", " ", ","]

    for char in characters_to_remove:
        input_string = input_string.replace(char, "")

    return input_string


# apt_adress1('8928')
# tmeps=get_apt_loca('8928')
# tmeps
# print(tmeps['latitude'])
# temp=get_jutack_list('5111012200')
# temp
# temp['articleList'][1]['articleNo']
# temp1=get_jutack_info('2331346831')
# temp1
# temp['articleList'][0]
# print(len(temp['articleList']))
# print("찐 출력")
# print(temp)

temp20 = get_jutack_info("2332128807")
temp20
print("끝")

import time

# 주택

# temmp["articleDetail"]['articleName']  #전원주택 type
# temmp["articleDetail"]['cityName']     # 강원도 si
# temmp["articleDetail"]['divisionName'] #  춘천시 gu
# temmp["articleDetail"]['sectionName']   #퇴계동 dong
# rm(temmp['articleAddition']['sameAddrMaxPrc']) #85300 maxdealprice
# rm(temmp['articleAddition']['sameAddrMinPrc'])  #85300 mindealprice
# temmp["articleDetail"]['latitude']              #37.~~ latitude
# temmp["articleDetail"]['longitude']             #127.~~ longitude

regionsdong = ""
regionsi = ""

dungdata3 = pd.DataFrame()
sido_list = get_sido_info()
# for m in range(len(sido_list)):
gungu_list = get_gungu_info(sido_list[9])
gungu_apt_list = [0] * len(gungu_list)
for j in range(1):  # 구 마다 하나씩 저장
    j = 9
    dong_list = get_dong_info(gungu_list[j])
    dong_apt_list = [0] * len(dong_list)
    print("\n")

    for k in range(1):  # 동마다 하나씩 저장
        # 변환 코드: asdf
        k = 6
        apt_list = get_jutack_list(dong_list[k])
        # apt_list_data=[0]*len(apt_list)
        if apt_list is not []:
            for n in range(len(apt_list)):  # 아파트 마다 하나씩 저장

                # temp_loca=get_apt_loca(apt_list[n])
                # print(temp)
                # print(temp["articleDetail"]['articleName'])
                temp_data = pd.DataFrame(
                    columns=['latitude', 'longitude', 'type', 'name', 'si', 'gu', 'dong', 'maxdealprice',
                             'mindealprice', 'maxlprice', 'minlprice', 'lrate', 'real1year', 'real1month',
                             'real1dealprice', 'real2year', 'real2month', 'real2dealprice', 'real3year', 'real3month',
                             'real3dealprice'])
                # temp_add=apt_adress(apt_list[n])

                # del temp22

                temp22 = get_jutack_info(apt_list[n])
                # print(temp22)
                if temp22 != {}:
                    print(temp22)
                    try:
                        temp_data.loc[n, "latitude"] = temp22["articleDetail"]['latitude']
                    except TypeError:
                        temp_data.loc[n, "latitude"] = "0"
                    # print(temp_loca["latitude"])
                    try:
                        temp_data.loc[n, "longitude"] = temp22["articleDetail"]['longitude']
                    except TypeError:
                        temp_data.loc[n, "latitude"] = "0"
                    # print(temp_loca["longitude"])
                    temp_data.loc[n, "type"] = "빌라/주택"
                    # print(temp["complexTypeName"])

                    temp_real = apt_price(apt_list[n])
                    # temp_real['realPriceOnMonthList'][0]['realPriceList'][0]['tradeYear']

                    # try:
                    temp_data.loc[n, "name"] = temp22["articleDetail"]['articleName']
                    print(temp22["articleDetail"]['articleName'])
                    # print(temp["complexName"])
                    # except KeyError:
                    # temp_data.loc[n,"name"]="No"

                    try:
                        temp_data.loc[n, "si"] = temp22["articleDetail"]['cityName']
                    except KeyError:
                        temp_data.loc[n, "si"] = "No"
                    try:
                        temp_data.loc[n, "gu"] = temp22["articleDetail"]['divisionName']
                        regionsi = temp22["articleDetail"]['divisionName']
                    except KeyError:
                        temp_data.loc[n, "gu"] = "No"
                    try:
                        temp_data.loc[n, "dong"] = temp22["articleDetail"]['sectionName']
                        regionsdong = temp22["articleDetail"]['sectionName']
                    except KeyError:
                        temp_data.loc[n, "dong"] = "No"

                    if temp22["articleDetail"]['tradeTypeName'] == '매매':
                        try:
                            temp_data.loc[n, "maxdealprice"] = rm(temp22['articleAddition']['sameAddrMaxPrc'])
                        except KeyError:
                            temp_data.loc[n, "maxdealprice"] = "0"
                        try:
                            temp_data.loc[n, "mindealprice"] = rm(temp22['articleAddition']['sameAddrMinPrc'])
                        except KeyError:
                            temp_data.loc[n, "mindealprice"] = "0"
                        try:
                            temp_data.loc[n, "maxlprice"] = "0"
                        except KeyError:
                            temp_data.loc[n, "maxlprice"] = "0"
                        try:
                            temp_data.loc[n, "minlprice"] = "0"
                        except KeyError:
                            temp_data.loc[n, "minlprice"] = "0"

                    else:
                        try:
                            temp_data.loc[n, "maxdealprice"] = "0"
                        except KeyError:
                            temp_data.loc[n, "maxdealprice"] = "0"
                        try:
                            temp_data.loc[n, "mindealprice"] = "0"
                        except KeyError:
                            temp_data.loc[n, "mindealprice"] = "0"
                        try:
                            temp_data.loc[n, "maxlprice"] = rm(temp22['articleAddition']['sameAddrMaxPrc'])
                        except KeyError:
                            temp_data.loc[n, "maxlprice"] = "0"
                        try:
                            temp_data.loc[n, "minlprice"] = rm(temp22['articleAddition']['sameAddrMinPrc'])
                        except KeyError:
                            temp_data.loc[n, "minlprice"] = "0"

                    try:
                        if temp22["maxPrice"] != 0:
                            temp_data.loc[n, "lrate"] = temp22["maxLeasePrice"] / temp22["maxPrice"]
                        else:
                            temp_data.loc[n, "lrate"] = "0"
                    except KeyError:
                        temp_data.loc[n, "lrate"] = "0"

                    try:
                        temp_data.loc[n, "real1year"] = "0"
                    except IndexError:
                        temp_data.loc[n, "real1year"] = "0"
                    except KeyError:
                        temp_data.loc[n, "real1year"] = "0"
                    try:
                        temp_data.loc[n, "real1month"] = "0"
                    except IndexError:
                        temp_data.loc[n, "real1month"] = "0"
                    except KeyError:
                        temp_data.loc[n, "real1month"] = "0"
                    try:
                        temp_data.loc[n, "real1dealprice"] = "0"
                    except IndexError:
                        temp_data.loc[n, "real1dealprice"] = "0"
                    except KeyError:
                        temp_data.loc[n, "real1dealprice"] = "0"

                    try:
                        temp_data.loc[n, "real2year"] = "0"
                    except IndexError:
                        temp_data.loc[n, "real2year"] = "0"
                    except KeyError:
                        temp_data.loc[n, "real2year"] = "0"

                    try:
                        temp_data.loc[n, "real2month"] = "0"
                    except IndexError:
                        temp_data.loc[n, "real2month"] = "0"
                    except KeyError:
                        temp_data.loc[n, "real2month"] = "0"

                    try:
                        temp_data.loc[n, "real2dealprice"] = "0"
                    except IndexError:
                        temp_data.loc[n, "real2dealprice"] = "0"
                    except KeyError:
                        temp_data.loc[n, "real2dealprice"] = "0"

                    try:
                        temp_data.loc[n, "real3year"] = "0"
                    except IndexError:
                        temp_data.loc[n, "real3year"] = "0"
                    except KeyError:
                        temp_data.loc[n, "real3year"] = "0"
                    try:
                        temp_data.loc[n, "real3month"] = "0"
                    except IndexError:
                        temp_data.loc[n, "real3month"] = "0"
                    except KeyError:
                        temp_data.loc[n, "real3month"] = "0"
                    try:
                        temp_data.loc[n, "real3dealprice"] = "0"
                    except IndexError:
                        temp_data.loc[n, "real3dealprice"] = "0"
                    except KeyError:
                        temp_data.loc[n, "real3dealprice"] = "0"

                    # print(temp_data)

                    dungdata3 = pd.concat([dungdata3, temp_data], ignore_index=True)
                    # count=count+1
                    dungdata3.to_csv("kangwon_ju(" + regionsi + "=" + regionsdong + ").csv", encoding="CP949")

                else:
                    print(temp22)
                    print("넘어갑니당")
                    print(k)
                    print(n)
                    dungdata3.to_csv("kangwon_ju(" + regionsi + "=" + regionsdong + ").csv", encoding="CP949")
                    continue

print("종료.")
dungdata3.to_csv("kangwon_ju(" + regionsi + "=" + regionsdong + ").csv", encoding="CP949")
dungdata3