export interface Member{
    id: number;
    name: string;
    email: string;
    phone?: string;
    birthDate?: string;
    emergencyContact?: string;
    medicalNotes?: string;
    createdAt: string;
}

export interface Membership {
    id: number;
    member: Member;
    planType: string;
    startDate: string;
    endDate: string;
    status: 'ACTIVATE' | 'EXPIRED' | 'CANCELLED';
    price: number;
}

export interface DashboardStats{
    totalMembers: number;
    activateMemberships: number;
    expiringMemberships: number;
    monthlyRevenue: number;
    newMembersThisMonth: number;
}

export interface ApiResponse<T>{
    data: T;
    status: number;
    statusText: string;
}