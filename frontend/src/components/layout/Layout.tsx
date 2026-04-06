import { Outlet } from 'react-router-dom';
import { Navbar } from './Navbar';
import { Toaster } from '@/components/ui/sonner';

export const Layout = () => {
  return (
    <div className="flex min-h-screen flex-col bg-background font-sans transition-colors duration-300">
      <Navbar />
      <main className="flex-1 overflow-x-hidden pt-16">
        <div className="container mx-auto px-4 py-8">
          <Outlet />
        </div>
      </main>
      <footer className="border-t bg-muted/30 py-8">
        <div className="container mx-auto px-4 text-center text-sm text-muted-foreground">
          <p>© 2026 MiniFood Ordering System. All rights reserved.</p>
        </div>
      </footer>
      <Toaster />
    </div>
  );
};
