import React, {useState, useEffect} from "react";
import { getDashboardStats, getExpiringMemberships } from "@/services/api";
import { DashboardStats, Membership } from "@/types";

const Dashboard: React.FC = () => {
    const [stats, setStats] = useState<DashboardStats | null>(null);
    const [expiringMemberships, setExpiringMemberships] = useState<Membership[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');


    const loadDashboardData = async () => {
        try{
            const [statsResponse, expiringResponse] = await Promise.all([
                getDashboardStats(),
                getExpiringMemberships(7)
            ]);

            setStats(statsResponse.data);
            setExpiringMemberships(expiringResponse.data);
            setLoading(false);

        }catch(err){
            setError('Error loading dashboard data');
            setLoading(false);
        }
    };

    if(loading) return <div className="loading">Loading...</div>;
    if(error) return <div className="error">Error: {error}</div>
    if(!stats) return <div>No data available</div>

    return (
        <div className="dashboard">
            <h2>Dashboard</h2>

            <div className="stats-grid">
                <div className="stat-card">
                    <h3>Total members</h3>
                    <p className="stat-number">{stats.totalMembers}</p>
                </div>
                <div className="stat-card">
                    <h3>Active members</h3>
                    <p className="stat-number">{stats.activateMemberships}</p>
                </div>
                <div className="stat-card">
                    <h3>Expiring this week</h3>
                    <p className="stat-number">{stats.expiringMemberships}</p>
                </div>
                <div className="stat-card">
                    <h3>Monthly revenue</h3>
                    <p className="stat-number">{stats.monthlyRevenue?.toFixed(2) || '0.00'}</p>
                </div>
                <div className="stat-card">
                    <h3>New members this month</h3>
                    <p className="stat-number">{stats.newMembersThisMonth}</p>
                </div>
            </div>

            <div className="dashboard-section">
                <h3>Memberships Expiring Soon</h3>
                {expiringMemberships.length === 0 ? (
                    <p>No memberships expiring in the next 7 days.</p>    
                ): (
                    <div className="expiring-list">
                        {expiringMemberships.map(membership => (
                            <div key={membership.id} className="expiring-item">
                                <div className="member-info">
                                    <strong>{membership.member?.name}</strong>
                                    <span>{membership.planType}</span>
                                </div>
                                <div className="expiration-info">
                                    <span>Expires: {new Date(membership.endDate).toLocaleDateString()}</span>
                                    <span className="status-badge active">Active</span>
                                </div>
                            </div>
                        ))}
                    </div>
                )}
            </div>

            <div className="quick-actions">
                <h3>Quick actions</h3>
                <div className="actions-buttons">
                    <button className="btn-primary">Add new member</button>
                    <button className="btn-primary">Create membership</button>
                    <button className="btn-secondary">View Reports</button>
                </div>
            </div>
        </div>
    );
};

export default Dashboard