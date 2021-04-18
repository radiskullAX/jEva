/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.animegame.jeva.irc;

/**
 *
 * @author radiskull
 */
public class CommandCode {

	public final static int RPL_WELCOME = 1;
	public final static int RPL_YOURHOST = 2;
	public final static int RPL_CREATED = 3;
	public final static int RPL_MYINFO = 4;
	public final static int RPL_BOUNCE = 5;

	public final static int RPL_TRACELINK = 200;
	public final static int RPL_TRACECONNECTING = 201;
	public final static int RPL_TRACEHANDSHAKE = 202;
	public final static int RPL_TRACEUNKNOWN = 203;
	public final static int RPL_TRACEOPERATOR = 204;
	public final static int RPL_TRACEUSER = 205;
	public final static int RPL_TRACESERVER = 206;
	public final static int RPL_TRACESERVICE = 207;
	public final static int RPL_TRACENEWTYPE = 208;
	public final static int RPL_TRACECLASS = 209;
	public final static int RPL_TRACERECONNECT = 210;
	public final static int RPL_STATSLINKINFO = 211;
	public final static int RPL_STATSCOMMANDS = 212;
	public final static int RPL_ENDOFSTATS = 219;
	public final static int RPL_UMODEIS = 221;
	public final static int RPL_SERVLIST = 234;
	public final static int RPL_SERVLISTEND = 235;
	public final static int RPL_STATSUPTIME = 242;
	public final static int RPL_STATSOLINE = 243;
	public final static int RPL_LUSERCLIENT = 251;
	public final static int RPL_LUSEROP = 252;
	public final static int RPL_LUSERUNKNOWN = 253;
	public final static int RPL_LUSERCHANNELS = 254;
	public final static int RPL_LUSERME = 255;
	public final static int RPL_ADMINME = 256;
	public final static int RPL_ADMINLOC1 = 257;
	public final static int RPL_ADMINLOC2 = 258;
	public final static int RPL_ADMINEMAIL = 259;
	public final static int RPL_TRACELOG = 261;
	public final static int RPL_TRACEEND = 262;
	public final static int RPL_TRYAGAIN = 263;

	public final static int RPL_AWAY = 301;
	public final static int RPL_USERHOST = 302;
	public final static int RPL_ISON = 303;
	public final static int RPL_UNAWAY = 305;
	public final static int RPL_NOWAWAY = 306;
	public final static int RPL_WHOISUSER = 311;
	public final static int RPL_WHOISSERVER = 312;
	public final static int RPL_WHOISOPERATOR = 313;
	public final static int RPL_WHOWASUSER = 314;
	public final static int RPL_ENDOFWHO = 315;
	public final static int RPL_WHOISIDLE = 317;
	public final static int RPL_ENDOFWHOIS = 318;
	public final static int RPL_WHOISCHANNELS = 319;
	public final static int RPL_LISTSTART = 321;
	public final static int RPL_LIST = 322;
	public final static int RPL_LISTEND = 323;
	public final static int RPL_CHANNELMODEIS = 324;
	public final static int RPL_UNIQOPIS = 325;
	public final static int RPL_NOTOPIC = 331;
	public final static int RPL_TOPIC = 332;
	public final static int RPL_INVITING = 341;
	public final static int RPL_SUMMONING = 342;
	public final static int RPL_INVITELIST = 346;
	public final static int RPL_ENDOFINVITELIST = 347;
	public final static int RPL_EXCEPTLIST = 348;
	public final static int RPL_ENDOFEXCEPTLIST = 349;
	public final static int RPL_VERSION = 351;
	public final static int RPL_WHOREPLY = 352;
	public final static int RPL_NAMREPLY = 353;
	public final static int RPL_LINKS = 364;
	public final static int RPL_ENDOFLINKS = 365;
	public final static int RPL_ENDOFNAMES = 366;
	public final static int RPL_BANLIST = 367;
	public final static int RPL_ENDOFBANLIST = 368;
	public final static int RPL_ENDOFWHOWAS = 369;
	public final static int RPL_INFO = 371;
	public final static int RPL_MOTD = 372;
	public final static int RPL_ENDOFINFO = 374;
	public final static int RPL_MOTDSTART = 375;
	public final static int RPL_ENDOFMOTD = 376;
	public final static int RPL_YOUREOPER = 381;
	public final static int RPL_REHASHING = 382;
	public final static int RPL_YOURESERVICE = 383;
	public final static int RPL_TIME = 391;
	public final static int RPL_USERSSTART = 392;
	public final static int RPL_USERS = 393;
	public final static int RPL_ENDOFUSERS = 394;
	public final static int RPL_NOUSERS = 395;

	public final static int ERR_NOSUCHNICK = 401;
	public final static int ERR_NOSUCHSERVER = 402;
	public final static int ERR_NOSUCHCHANNEL = 403;
	public final static int ERR_CANNOTSENDTOCHAN = 404;
	public final static int ERR_TOOMANYCHANNELS = 405;
	public final static int ERR_WASNOSUCHNICK = 406;
	public final static int ERR_TOOMANYTARGETS = 407;
	public final static int ERR_NOSUCHSERVICE = 408;
	public final static int ERR_NOORIGIN = 409;
	public final static int ERR_NORECIPIENT = 411;
	public final static int ERR_NOTEXTTOSEND = 412;
	public final static int ERR_NOTOPLEVEL = 413;
	public final static int ERR_WILDTOPLEVEL = 414;
	public final static int ERR_BADMASK = 415;
	public final static int ERR_UNKNOWNCOMMAND = 421;
	public final static int ERR_NOMOTD = 422;
	public final static int ERR_NOADMININFO = 423;
	public final static int ERR_FILEERROR = 424;
	public final static int ERR_NONICKNAMEGIVEN = 431;
	public final static int ERR_ERRONEUSNICKNAME = 432;
	public final static int ERR_NICKNAMEINUSE = 433;
	public final static int ERR_NICKCOLLISION = 436;
	public final static int ERR_UNAVAILRESOURCE = 437;
	public final static int ERR_USERNOTINCHANNEL = 441;
	public final static int ERR_NOTONCHANNEL = 442;
	public final static int ERR_USERONCHANNEL = 443;
	public final static int ERR_NOLOGIN = 444;
	public final static int ERR_SUMMONDISABLED = 445;
	public final static int ERR_USERSDISABLED = 446;
	public final static int ERR_NOTREGISTERED = 451;
	public final static int ERR_NEEDMOREPARAMS = 461;
	public final static int ERR_ALREADYREGISTRED = 462;
	public final static int ERR_NOPERMFORHOST = 463;
	public final static int ERR_PASSWDMISMATCH = 464;
	public final static int ERR_YOUREBANNEDCREEP = 465;
	public final static int ERR_YOUWILLBEBANNED = 466;
	public final static int ERR_KEYSET = 467;
	public final static int ERR_CHANNELISFULL = 471;
	public final static int ERR_UNKNOWNMODE = 472;
	public final static int ERR_INVITEONLYCHAN = 473;
	public final static int ERR_BANNEDFROMCHAN = 474;
	public final static int ERR_BADCHANNELKEY = 475;
	public final static int ERR_BADCHANMASK = 476;
	public final static int ERR_NOCHANMODES = 477;
	public final static int ERR_BANLISTFULL = 478;
	public final static int ERR_NOPRIVILEGES = 481;
	public final static int ERR_CHANOPRIVSNEEDED = 482;
	public final static int ERR_CANTKILLSERVER = 483;
	public final static int ERR_RESTRICTED = 484;
	public final static int ERR_UNIQOPPRIVSNEEDED = 485;
	public final static int ERR_NOOPERHOST = 491;

	public final static int ERR_UMODEUNKNOWNFLAG = 501;
	public final static int ERR_USERSDONTMATCH = 502;

}
