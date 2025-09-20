import React, {useState, useEffect} from "react";
import { deleteMember, getMembers } from "@/services/api";
import { Link } from 'react-router-dom';
import { Member } from "@/types";

const MemberList: React.FC = ()=> {
    const [members, setMembers] = useState<Member[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() =>{
        loadMembers();
    },[]);

    const loadMembers = async () => {
        try{
            const response = await getMembers();
            setMembers(response.data);
            setLoading(false);
        }catch(err){
            setError('Error loading members');
            setLoading(false);
        }
    };

    const handleDelete = async (id: number) => {
        if(window.confirm('Are you sure you want to delete this member?')){
            try{
                await deleteMember(id);
                loadMembers();
            }catch(err){
                setError('Error deliting member');
            }
        }
    };

    if (loading) return <div className="loading">Loading...</div>
    if(error) return <div className="error">Error: {error}</div>

    return(
        <div className="member-list">
            <div className="page-header">
                <h2>Member management</h2>
                <Link to="/members/new" className="btn-primary">
                    Add new member
                </Link>
            </div>

            <table className="data-table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Birth Date</th>
                        <th>Emergency contact</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {members.map(member=>(
                        <tr key={member.id}>
                            <td>
                                <strong>{member.name}</strong>
                            </td>
                            <td>{member.email}</td>
                            <td>{member.phone || 'N/A'}</td>
                            <td>{member.birthDate ? new Date(member.birthDate).toLocaleDateString(): 'N/A'}</td>
                            <td>{member.emergencyContact || 'N/A'}</td>
                            <td>
                                <div className="action-buttons">
                                    <Link to={`/members/edit/${member.id}`} className="btn-edit">
                                        Edit
                                    </Link>
                                    <button 
                                    className="btn-delete"
                                    onClick={() => handleDelete(member.id)}
                                    >
                                        Delete
                                    </button>
                                    <Link to={`/members/${member.id}/memberships`} className="btn-view">
                                        View Memberships
                                    </Link>
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

            {members.length === 0 && (
                <div className="empty-stats">
                    <h3>No members found</h3>
                    <p>Start by adding your first member to the system</p>
                    <Link to="/members/new" className="btn-primary">
                        Add first member
                    </Link>
                </div>
            )}
        </div>
    )
};


export default MemberList;