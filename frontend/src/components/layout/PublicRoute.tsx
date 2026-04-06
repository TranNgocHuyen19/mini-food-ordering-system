import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '@/context/AuthContext';

export const PublicRoute = () => {
  const { user, loading } = useAuth();

  if (loading) {
    return <div className="h-screen w-full flex items-center justify-center">Loading...</div>;
  }

  // Nếu đã đăng nhập, chuyển hướng khỏi trang login/register sang trang chủ
  if (user) {
    return <Navigate to="/" replace />;
  }

  return <Outlet />;
};
