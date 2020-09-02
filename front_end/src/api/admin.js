import { axios } from '@/utils/request'
const api = {
    adminPre: '/api/admin'
}
export function getManagerListAPI(){
    return axios({
        url: `${api.adminPre}/getAllManagers`,
        method: 'POST'
    })
}
export function addClientAPI(client,activeUserId){
    return axios({
        url: `${api.adminPre}/${client}/${activeUserId}/addClient`,
        method: 'POST',
    })
}
export function getClientListAPI(){
    return axios({
        url: `${api.adminPre}/getAllClients`,
        method: 'POST'
    })
}
export function addManagerAPI(data) {
    return axios({
        url: `${api.adminPre}/addManager`,
        method: 'POST',
        data
    })
}
export function deleteUserAPI(userId){
    return axios({
        url: `${api.adminPre}/${userId}/deleteUser`,
        method: 'POST',
    })
}