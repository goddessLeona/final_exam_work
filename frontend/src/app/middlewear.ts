import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";

export function middleware(request: NextRequest) {
  const { pathname } = request.nextUrl;

  // Protect /member routes only
  if (pathname.startsWith("/member")) {
    // Check for auth cookie (replace 'token' with your actual cookie name)
    const token = request.cookies.get("token");

    if (!token) {
      // Redirect unauthorized users to login page
      return NextResponse.redirect(new URL("/login", request.url));
    }
  }

  // Allow the request to continue for other routes
  return NextResponse.next();
}
