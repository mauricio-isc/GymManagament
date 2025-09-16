import axios from "axios";
import { Member, Membership, DashboardStats } from "@/types";

const API_BASE_URL = '/api';

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

//MIEMBROS API 
export const getMembers = (): Promise<{data: Member[] }> => 
    api.get('/members');
export const getMember = (id: number): Promise<{ data: Member }> => 
    api.get(`/members/${id}`);
export const createMember = (member: Omit<Member, 'id' | 'createdAt'>): Promise< {data: Member}> =>
    api.post('/members', member);
export const updateMember = (id: number, member: Partial<Member>): Promise<{ data: Member }> =>
    api.put(`/members/${id}`, member);
export const deleteMember = (id: number): Promise<void> => api.delete(`/members/${id}`);

//Membresias de la API
export const getMemberships = (): Promise<{ data: Membership[] }> => 
    api.get('/memberships');

export const getMembership = (id: number): Promise<{ data: Membership }> =>
    api.get(`/memberships/${id}`);

export const getMembershipsByMember = (memberId: number): Promise<{ data: Membership[] }> =>
    api.get(`/memberships/${memberId}`);

export const createMembership = (membership: Omit<Membership, 'id'>): Promise<{ data: Membership }> =>
    api.post('/membership', membership);

export const updateMembership = (id: number, membership: Partial<Membership>):
Promise<{ data: Membership }> =>
    api.put(`/memberships/${id}`,membership);

export const deleteMembership = (id: number): Promise<void> => 
    api.delete(`/memberships/${id}`);

export const getExpiringMemberships = (days: number=7): Promise<{ data: Membership[] }> =>
    api.get(`/memberships/expiring?days=${days}`);

export const getMembershipsByStatus = (status: string): Promise<{ data: Membership[] }> =>
    api.get(`/memberships/status/${status}`);

export const isMembershipActive = (memberId: number): Promise<{ data: boolean }> =>
    api.get(`/memberships/member/${memberId}/active`);

//dashboard api
export const getDashboardStats = (): Promise<{ data: DashboardStats }> => api.get('/dashboard/stats');

export default api;