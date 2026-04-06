import { motion } from 'framer-motion';
import { useAuth } from '@/context/AuthContext';
import { Card, CardHeader, CardTitle, CardDescription, CardContent } from '@/components/ui/card';
import { User, Shield, Calendar } from 'lucide-react';
import { Badge } from '@/components/ui/badge';

export const Profile = () => {
  const { user } = useAuth();

  if (!user) return null;

  return (
    <div className="container mx-auto py-12 px-4 max-w-4xl">
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        className="space-y-8"
      >
        <div className="flex flex-col md:flex-row items-center gap-6">
          <div className="w-32 h-32 rounded-full bg-primary/10 flex items-center justify-center border-4 border-primary/20 shadow-xl">
            <User size={64} className="text-primary" />
          </div>
          <div className="text-center md:text-left">
            <h1 className="text-4xl font-black tracking-tight">{user.username}</h1>
            <p className="text-muted-foreground flex items-center justify-center md:justify-start gap-2 mt-2">
              <Shield size={16} />
              Role: {user.role || 'User'}
            </p>
            <div className="flex gap-2 mt-4 justify-center md:justify-start">
              <Badge variant="secondary" className="rounded-full px-3 py-1 font-bold tracking-wider uppercase text-[10px]">
                <Shield size={10} className="mr-1" />
                Customer
              </Badge>
              <Badge variant="outline" className="rounded-full px-3 py-1 font-bold tracking-wider uppercase text-[10px]">
                <Calendar size={10} className="mr-1" />
                Joined March 2024
              </Badge>
            </div>
          </div>
        </div>

        <Card className="border-none shadow-2xl bg-card/50 backdrop-blur-xl overflow-hidden">
          <CardHeader className="border-b bg-muted/30 pb-6 pt-8">
            <CardTitle className="text-2xl font-bold">Account Information</CardTitle>
            <CardDescription>Manage your personal details and account settings</CardDescription>
          </CardHeader>
          <CardContent className="grid gap-8 p-8">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
              <div className="space-y-2">
                <span className="text-xs font-black uppercase tracking-widest text-muted-foreground">Username</span>
                <p className="text-lg font-semibold bg-background/50 p-3 rounded-xl border border-border/40">
                  {user.username}
                </p>
              </div>
              <div className="space-y-4">
                <span className="text-xs font-black uppercase tracking-widest text-muted-foreground">Account Role</span>
                <p className="text-lg font-semibold bg-background/50 p-3 rounded-xl border border-border/40 uppercase tracking-widest">
                  {user.role || 'User'}
                </p>
              </div>
            </div>
            
            <div className="pt-4 border-t border-border/40">
              <h3 className="text-sm font-black uppercase tracking-widest text-primary mb-4">Privacy & Security</h3>
              <p className="text-sm text-muted-foreground leading-relaxed italic">
                Your account is protected with end-to-end encryption. For security reasons, sensitive actions like changing your password require additional verification.
              </p>
            </div>
          </CardContent>
        </Card>
      </motion.div>
    </div>
  );
};
